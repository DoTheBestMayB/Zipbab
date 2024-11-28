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
import com.bestapp.zipbab.data.mapper.toDomain
import com.bestapp.zipbab.data.model.remote.user.ProfilePostResponse
import com.bestapp.zipbab.data.remote.datasource.ProfilePostRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.StorageRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.UserRemoteDataSource
import com.bestapp.zipbab.data.remote.upload.UploadStateDto
import com.bestapp.zipbab.data.remote.upload.UploadWorker
import com.bestapp.zipbab.domain.model.UploadState
import com.bestapp.zipbab.domain.model.user.User
import com.bestapp.zipbab.domain.repository.UserRepository
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import com.bestapp.zipbab.domain.util.map
import com.bestapp.zipbab.domain.util.onError
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import java.util.UUID
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val profilePostRemoteDataSource: ProfilePostRemoteDataSource,
    private val storageRemoteDataSource: StorageRemoteDataSource,
    @ApplicationContext private val context: Context,
) : UserRepository {

    private val workManager = WorkManager.getInstance(context)
    private val jsonAdapter = Json {
        ignoreUnknownKeys = true
    }

    private suspend fun fetchProfilePostById(id: String): ProfilePostResponse {
        return when (val response = profilePostRemoteDataSource.getPost(id)) {
            is Result.Error -> ProfilePostResponse() // TODO : 빈 값을 반환 하는 대신 더 나은 방법이 있을까?
            is Result.Success -> response.data
        }
    }

    override suspend fun getUser(userId: String): Result<User, NetworkError> {
        return userRemoteDataSource.getUser(userId).map {
            it.toDomain(fetchProfilePostById = ::fetchProfilePostById)
        }
    }

    override suspend fun updateUserNickname(
        userId: String,
        nickname: String
    ): Result<Boolean, NetworkError> {
        return userRemoteDataSource.updateUserNickname(userId, nickname)
    }

    override suspend fun updateUserProfileImage(
        userId: String,
        profileImageUri: String
    ): Result<Boolean, NetworkError> {
        val imagePath = if (profileImageUri.isBlank()) {
            ""
        } else {
            when (val response =
                storageRemoteDataSource.uploadImage("userProfile", Uri.parse(profileImageUri))) {
                is Result.Error -> return response
                is Result.Success -> response.data
            }
        }

        // 기존 프로필 이미지 삭제
        val userResponse = when (val result = userRemoteDataSource.getUser(userId)) {
            is Result.Error -> return result
            is Result.Success -> result.data
        }
        storageRemoteDataSource.deleteImage(userResponse.profileImageUrl)
            .onError {
                // TODO : Firebase analytics 추가
            }

        // 프로필 이미지 갱신
        return userRemoteDataSource.updateUserProfileImage(userId, imagePath)
    }

    override fun addPostWithWorkManager(
        workRequestKey: UUID,
        userId: String,
        tempPostDocumentID: String,
        images: List<String>
    ): Flow<UploadState> {
        val inputData = Data.Builder()
            .putString(UploadWorker.UPLOAD_USER_ID_KEY, userId)
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
                        WorkInfo.State.ENQUEUED -> UploadState.Pending(tempPostDocumentID)
                        WorkInfo.State.RUNNING -> UploadState.Pending(tempPostDocumentID)
                        WorkInfo.State.SUCCEEDED -> {
                            val tPostDocumentID =
                                it.outputData.getString(UploadWorker.RESULT_TEMP_POST_DOCUMENT_ID_KEY)
                                    ?: return@map UploadState.Fail(tempPostDocumentID)
                            val postDocumentID =
                                it.outputData.getString(UploadWorker.RESULT_POST_DOCUMENT_ID_KEY)
                                    ?: return@map UploadState.Fail(tempPostDocumentID)
                            return@map UploadState.SuccessPost(
                                tPostDocumentID,
                                postDocumentID
                            )
                        }

                        WorkInfo.State.FAILED -> UploadState.Fail(tempPostDocumentID)
                        WorkInfo.State.BLOCKED -> UploadState.Pending(tempPostDocumentID)
                        WorkInfo.State.CANCELLED -> UploadState.Fail(tempPostDocumentID)
                    }
                }
            jsonAdapter.decodeFromString<UploadStateDto>(jsonString).toDomain()
        }
    }
}
