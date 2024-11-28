package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.user.UserResponse
import com.bestapp.zipbab.data.networking.safeFirebaseCall
import com.bestapp.zipbab.data.remote.firestoreDB.FirestoreDB
import com.bestapp.zipbab.data.remote.util.doneSuccessful
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class UserRemoteDataSourceImpl @Inject constructor(
    private val firestoreDB: FirestoreDB,
) : UserRemoteDataSource {
    override suspend fun getUser(userId: String): Result<UserResponse, NetworkError> {
        return safeFirebaseCall {
            val user = firestoreDB.getUserDB().document(userId)
                .get()
                .await()

            user.toObject<UserResponse>()
        }
    }

    override suspend fun updateUserNickname(
        userId: String,
        nickname: String
    ): Result<Boolean, NetworkError> {
        return safeFirebaseCall {
            firestoreDB.getUserDB().document(userId)
                .update("nickname", nickname)
                .doneSuccessful()
        }
    }

    override suspend fun updateUserProfileImage(
        userId: String,
        imagePath: String
    ): Result<Boolean, NetworkError> {
        return safeFirebaseCall {
            val querySnapshot = firestoreDB.getUserDB().whereEqualTo("id", userId)
                .get()
                .await()

            val document = querySnapshot.documents.firstOrNull() ?: throw IllegalArgumentException()

            document.reference.update("profileUrl", imagePath)
                .doneSuccessful()
        }
    }

    override suspend fun addPost(
        userId: String,
        postDocumentId: String
    ): Result<Boolean, NetworkError> {
        return safeFirebaseCall {
            val querySnapshot = firestoreDB.getUserDB().whereEqualTo("id", userId)
                .get()
                .await()

            val document = querySnapshot.documents.firstOrNull() ?: throw IllegalArgumentException()

            document.reference.update("posts", FieldValue.arrayUnion(postDocumentId))
                .doneSuccessful()
        }
    }

    override suspend fun removePost(
        userId: String,
        postDocumentId: String
    ): Result<Boolean, NetworkError> {
        return safeFirebaseCall {
            val querySnapshot = firestoreDB.getUserDB().whereEqualTo("id", userId)
                .get()
                .await()

            val document = querySnapshot.documents.firstOrNull() ?: throw IllegalArgumentException()

            document.reference.update("posts", FieldValue.arrayRemove(postDocumentId))
                .doneSuccessful()
        }
    }
}
