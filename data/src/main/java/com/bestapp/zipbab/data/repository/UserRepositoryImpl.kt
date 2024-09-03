package com.bestapp.zipbab.data.repository

import android.content.Context
import android.net.Uri
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.bestapp.zipbab.data.model.UploadStateEntity
import com.bestapp.zipbab.data.model.local.SignOutEntity
import com.bestapp.zipbab.data.model.remote.LoginResponse
import com.bestapp.zipbab.data.model.remote.NotificationType
import com.bestapp.zipbab.data.model.remote.NotificationTypeResponse
import com.bestapp.zipbab.data.model.remote.Review
import com.bestapp.zipbab.data.model.remote.SignUpResponse
import com.bestapp.zipbab.data.model.remote.UserResponse
import com.bestapp.zipbab.data.remote.firestoreDB.FirestoreDB
import com.bestapp.zipbab.data.remote.datasource.UserLocalDataSource
import com.bestapp.zipbab.data.remote.datasource.UserRemoteDataSource
import com.bestapp.zipbab.data.remote.upload.UploadWorker
import com.bestapp.zipbab.data.remote.util.doneSuccessful
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.toObject
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val firestoreDB: FirestoreDB,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val storageRepository: StorageRepository,
    private val meetingRepository: MeetingRepository,
    private val postRepository: PostRepository,
    private val userLocalDataSource: UserLocalDataSource,
    @ApplicationContext private val context: Context,
    moshi: Moshi,
) : UserRepository {

    private val workManager = WorkManager.getInstance(context)
    private val jsonAdapter = moshi.adapter(UploadStateEntity::class.java)

    private val timeParseFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.KOREA)

    override suspend fun getUser(userDocumentID: String): UserResponse {
        return userRemoteDataSource.getUser(userDocumentID)
    }

    override suspend fun login(id: String, pw: String): LoginResponse {
        return userRemoteDataSource.login(id, pw)
    }

    override suspend fun signUpUser(
        nickname: String,
        email: String,
        password: String
    ): SignUpResponse {
        return userRemoteDataSource.signUpUser(nickname, email, password)
    }

    override suspend fun signOutUser(userDocumentID: String): SignOutEntity {
        // 회원탈퇴가 허용되지 않은 아이디인지 확인
        if (userRemoteDataSource.checkSignOutIsNotAllowed(userDocumentID)) {
            return SignOutEntity.IsNotAllowed
        }

        // 참여중인 모임 정리하기
        val meetings = meetingRepository.getMeetingByUserDocumentID(userDocumentID) +
                meetingRepository.getPendingMeetingByUserDocumentID(userDocumentID)

        for (meeting in meetings) {
            if (meeting.hostUserDocumentID == userDocumentID) {
                meetingRepository.deleteMeeting(meeting.meetingDocumentID)
            } else {
                meetingRepository.deleteMeetingMember(meeting.meetingDocumentID, userDocumentID)
            }
        }

        // 작성한 포스트 삭제하기
        val posts = postRepository.getPosts(userDocumentID)
        for (post in posts) {
            postRepository.deletePost(userDocumentID, post.postDocumentID)
        }

        // 프로필 이미지 삭제하기
        deleteUserProfileImage(userDocumentID)

        // 회원 탈퇴하기
        val isSuccess = userRemoteDataSource.signOutUser(userDocumentID)

        if (isSuccess.not()) {
            return SignOutEntity.Fail
        }

        // 로컬에서 사용자 documentID 삭제하기
        val isRemoveUserDocumentSuccess = userLocalDataSource.removeUserDocumentId()

        return if (isRemoveUserDocumentSuccess) {
            SignOutEntity.Success
        } else {
            SignOutEntity.Fail
        }
    }

    override suspend fun updateUserNickname(userDocumentID: String, nickname: String): Boolean {
        return userRemoteDataSource.updateUserNickname(userDocumentID, nickname)
    }

    override suspend fun updateUserTemperature(reviews: List<Review>): Boolean {
        return userRemoteDataSource.updateUserTemperature(reviews)
    }

    override suspend fun updateUserMeetingCount(userDocumentID: String): Boolean {
        return userRemoteDataSource.updateUserMeetingCount(userDocumentID)
    }

    override suspend fun updateUserProfileImage(
        userDocumentID: String,
        profileImageUri: String?
    ): Boolean {
        val uri = if (profileImageUri.isNullOrBlank()) {
            ""
        } else {
            storageRepository.uploadImage(
                Uri.parse(profileImageUri)
            )
        }

        // 기존 프로필 이미지 삭제
        deleteUserProfileImage(userDocumentID)

        // 프로필 이미지 갱신
        return userRemoteDataSource.updateProfileImage(userDocumentID, uri)
    }

    override suspend fun deleteUserProfileImage(userDocumentID: String) {
        val userResponse = userRemoteDataSource.getUser(userDocumentID)
        storageRepository.deleteImage(userResponse.profileImage)
    }

    override fun addPostWithAsync(
        userDocumentID: String,
        tempPostDocumentID: String,
        images: List<String>
    ): Flow<UploadStateEntity> = flow {
        emit(UploadStateEntity.Pending(tempPostDocumentID))

        val imageUrls = mutableListOf<String>()
        for ((idx, image) in images.withIndex()) {
            emit(
                UploadStateEntity.ProcessImage(
                    tempPostDocumentID,
                    idx + 1,
                    images.size,
                )
            )
            val url = storageRepository.uploadImage(
                Uri.parse(image)
            )
            imageUrls.add(url)
        }

        emit(UploadStateEntity.ProcessPost(tempPostDocumentID))

        val postDocumentId = postRepository.createPost(imageUrls)
        val isSuccess = userRemoteDataSource.addPost(userDocumentID, postDocumentId)
        if (isSuccess) {
            emit(UploadStateEntity.SuccessPost(tempPostDocumentID, postDocumentId))
        } else {
            emit(UploadStateEntity.Fail(tempPostDocumentID))
        }
    }

    override fun addPostWithWorkManager(
        workRequestKey: UUID,
        userDocumentID: String,
        tempPostDocumentID: String,
        images: List<String>
    ): Flow<UploadStateEntity> {
        val inputData = Data.Builder()
            .putString(UploadWorker.UPLOAD_USER_DOCUMENT_ID_KEY, userDocumentID)
            .putString(UploadWorker.UPLOAD_TEMP_POST_DOCUMENT_ID_KEY, tempPostDocumentID)
            .putStringArray(UploadWorker.UPLOAD_IMAGES_KEY, images.toTypedArray())
            .build()
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val request = OneTimeWorkRequestBuilder<UploadWorker>()
            .setId(workRequestKey)
            .setInputData(inputData)
            .setConstraints(constraints)
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()
        workManager.beginWith(request)
            .enqueue()

        return workManager.getWorkInfoByIdFlow(workRequestKey).map {
            val jsonString = it.progress.getString(UploadWorker.PROGRESS_KEY)
                ?: run {
                    return@map when (it.state) {
                        WorkInfo.State.ENQUEUED -> UploadStateEntity.Pending(tempPostDocumentID)
                        WorkInfo.State.RUNNING -> UploadStateEntity.Pending(tempPostDocumentID)
                        WorkInfo.State.SUCCEEDED -> {
                            val tPostDocumentID =
                                it.outputData.getString(UploadWorker.RESULT_TEMP_POST_DOCUMENT_ID_KEY)
                                    ?: return@map UploadStateEntity.Fail(tempPostDocumentID)
                            val postDocumentID =
                                it.outputData.getString(UploadWorker.RESULT_POST_DOCUMENT_ID_KEY)
                                    ?: return@map UploadStateEntity.Fail(tempPostDocumentID)
                            return@map UploadStateEntity.SuccessPost(
                                tPostDocumentID,
                                postDocumentID
                            )
                        }

                        WorkInfo.State.FAILED -> UploadStateEntity.Fail(tempPostDocumentID)
                        WorkInfo.State.BLOCKED -> UploadStateEntity.Pending(tempPostDocumentID)
                        WorkInfo.State.CANCELLED -> UploadStateEntity.Fail(tempPostDocumentID)
                    }
                }
            jsonAdapter.fromJson(jsonString) ?: UploadStateEntity.Pending(tempPostDocumentID)
        }
    }

    override suspend fun getAccessToken(): com.bestapp.zipbab.data.remote.notification.fcm.AccessToken {
        val querySnapshot = firestoreDB.getAccessDB().document("n9FI6noeU2dFTHbHdQd8")
            .get()
            .await()

        return querySnapshot.toObject<com.bestapp.zipbab.data.remote.notification.fcm.AccessToken>() ?: com.bestapp.zipbab.data.remote.notification.fcm.AccessToken()
    }

    override suspend fun removeItem(
        udi: String,
        exchange: List<NotificationTypeResponse>,
        index: Int
    ): Boolean {

        return firestoreDB.getUsersDB().document(udi)
            .update("notifications", exchange)
            .doneSuccessful()
    }

    override suspend fun addNotification(
        type: NotificationType,
        userDocumentID: String,
        meetingDocumentID: String,
        hostDocumentID: String
    ): Boolean {
        val notification = hashMapOf(
            "meetingDocumentID" to meetingDocumentID,
            "type" to type.name,
            "uploadDate" to timeParseFormat.format(System.currentTimeMillis()),
            "userDocumentID" to userDocumentID,
        )

        return firestoreDB.getUsersDB().document(hostDocumentID)
            .update("notifications", FieldValue.arrayUnion(notification))
            .doneSuccessful()
    }
}