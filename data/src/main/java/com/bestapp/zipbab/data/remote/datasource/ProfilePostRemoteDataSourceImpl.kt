package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.user.ProfilePostResponse
import com.bestapp.zipbab.data.networking.safeFirebaseCall
import com.bestapp.zipbab.data.remote.firestoreDB.FirestoreDB
import com.bestapp.zipbab.data.remote.util.doneSuccessful
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class ProfilePostRemoteDataSourceImpl @Inject constructor(
    private val firestoreDB: FirestoreDB,
) : ProfilePostRemoteDataSource {

    override suspend fun deletePost(
        userId: String,
        postDocumentId: String
    ): Result<Boolean, NetworkError> {
        // 포스트 삭제, 사용자 정보에서 해당 포스트 삭제
        return safeFirebaseCall {
            val postDocumentRef = firestoreDB.getPostDB().document(postDocumentId)
            val userRef = firestoreDB.getUsersDB().document(userId)

            firestoreDB.runTransaction { transaction ->
                transaction.delete(postDocumentRef)
                transaction.update(userRef, "posts", FieldValue.arrayRemove(postDocumentId))
            }.doneSuccessful()
        }
    }

    override suspend fun getPost(postDocumentId: String): Result<ProfilePostResponse, NetworkError> {
        return safeFirebaseCall {
            firestoreDB.getPostDB().document(postDocumentId)
                .get()
                .await()
                .toObject<ProfilePostResponse>()
        }
    }

    override suspend fun createPost(
        userId: String,
        imagePaths: List<String>
    ): Result<String, NetworkError> {
        return safeFirebaseCall {
            val documentRef = firestoreDB.getPostDB().document()

            val data = mapOf(
                "writer" to userId,
                "images" to imagePaths,
                "createdAt" to System.currentTimeMillis(),
            )
            documentRef.set(data).await()

            documentRef.id
        }
    }
}
