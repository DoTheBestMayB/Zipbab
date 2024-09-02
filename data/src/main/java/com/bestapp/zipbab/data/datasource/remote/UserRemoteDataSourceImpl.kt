package com.bestapp.zipbab.data.datasource.remote

import com.bestapp.zipbab.data.FirestoreDB.FirestoreDB
import com.bestapp.zipbab.data.doneSuccessful
import com.bestapp.zipbab.data.model.remote.LoginResponse
import com.bestapp.zipbab.data.model.remote.PlaceLocation
import com.bestapp.zipbab.data.model.remote.Review
import com.bestapp.zipbab.data.model.remote.SignOutForbiddenResponse
import com.bestapp.zipbab.data.model.remote.SignUpResponse
import com.bestapp.zipbab.data.model.remote.UserResponse
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class UserRemoteDataSourceImpl @Inject constructor(
    private val firestoreDB: FirestoreDB,
) : UserRemoteDataSource {
    override suspend fun getUser(userDocumentID: String): UserResponse {
        val users = firestoreDB.getUsersDB()
            .whereEqualTo("userDocumentID", userDocumentID)
            .get()
            .await()

        for (document in users) {
            return document.toObject<UserResponse>()
        }

        return UserResponse()
    }

    override suspend fun login(id: String, pw: String): LoginResponse {
        val users = firestoreDB.getUsersDB()
            .whereEqualTo("id", id)
            .get()
            .await()

        for (document in users) {
            val userResponse = document.toObject<UserResponse>()
            if (userResponse.pw == pw) {
                return LoginResponse.Success(
                    userResponse.userDocumentID
                )
            }
        }
        return LoginResponse.Fail
    }

    override suspend fun signUpUser(
        nickname: String,
        email: String,
        password: String
    ): SignUpResponse {
        // 이메일 중복 가입 확인
        val users = firestoreDB.getUsersDB()
            .whereEqualTo("id", email)
            .get()
            .await()
        if (users.isEmpty.not()) {
            return SignUpResponse.DuplicateEmail
        }

        // 계정 등록
        val userResponse = UserResponse(
            userDocumentID = "",
            nickname = nickname,
            id = email,
            pw = password,
            profileImage = "",
            temperature = 36.5,
            meetingCount = 0,
            notifications = listOf(),
            meetingReviews = listOf(),
            posts = listOf(),
            placeLocation = PlaceLocation(
                locationAddress = "",
                locationLat = "",
                locationLong = ""
            )
        )
        val userDocumentRef = firestoreDB.getUsersDB()
            .add(userResponse)
            .await()
        val userDocumentID = userDocumentRef.id

        val isSuccess = firestoreDB.getUsersDB().document(userDocumentID)
            .update("userDocumentID", userDocumentID)
            .doneSuccessful()

        return if (isSuccess) {
            SignUpResponse.Success(userDocumentID)
        } else {
            firestoreDB.getUsersDB().document(userDocumentID)
                .delete()
            SignUpResponse.Fail
        }
    }

    override suspend fun checkSignOutIsNotAllowed(userDocumentID: String): Boolean {
        val documentSnapShot = firestoreDB.getPolicyDB()
            .document("ForbiddenForDelete")
            .get()
            .await()

        val notAllowedIDs = documentSnapShot.toObject<SignOutForbiddenResponse>()
        return notAllowedIDs?.userDocumentIDs?.contains(userDocumentID) ?: false
    }

    override suspend fun signOutUser(userDocumentID: String): Boolean {
        return firestoreDB.getUsersDB().document(userDocumentID)
            .delete()
            .doneSuccessful()
    }

    override suspend fun updateUserNickname(userDocumentID: String, nickname: String): Boolean {
        return firestoreDB.getUsersDB().document(userDocumentID)
            .update("nickname", nickname)
            .doneSuccessful()
    }

    override suspend fun updateUserTemperature(reviews: List<Review>): Boolean {
        return firestoreDB.runTransaction { transaction ->
            for (review in reviews) {
                if (review.votingPoint == 0.0) {
                    continue
                }
                transaction.update(
                    firestoreDB.getUsersDB().document(review.id),
                    "temperature",
                    FieldValue.increment(review.votingPoint)
                )
            }
        }.doneSuccessful()
    }

    override suspend fun updateUserMeetingCount(userDocumentID: String): Boolean {
        return firestoreDB.getUsersDB().document(userDocumentID)
            .update("meetingCount", FieldValue.increment(1))
            .doneSuccessful()
    }

    override suspend fun updateProfileImage(userDocumentID: String, imageUri: String): Boolean {
        return firestoreDB.getUsersDB().document(userDocumentID)
            .update("profileImage", imageUri)
            .doneSuccessful()
    }

    override suspend fun addPost(userDocumentID: String, postDocumentId: String): Boolean {
        return firestoreDB.getUsersDB().document(userDocumentID)
            .update("posts", FieldValue.arrayUnion(postDocumentId))
            .doneSuccessful()
    }
}