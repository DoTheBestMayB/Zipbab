package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.PostForInit
import com.bestapp.zipbab.data.model.remote.PostResponse
import com.bestapp.zipbab.data.model.remote.UserResponse
import com.bestapp.zipbab.data.remote.firestoreDB.FirestoreDB
import com.bestapp.zipbab.data.remote.util.doneSuccessful
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class PostRemoteDataSourceImpl @Inject constructor(
    private val firestoreDB: FirestoreDB,
) : PostRemoteDataSource {
    override suspend fun getPosts(userDocumentID: String): List<PostResponse> {
        val users = firestoreDB.getUsersDB()
            .whereEqualTo("userDocumentID", userDocumentID)
            .get()
            .await()

        val userResponse = users.first().toObject<UserResponse>()

        val postResponses = mutableListOf<PostResponse>()
        for (postDocumentId in userResponse.posts) {
            val responses = firestoreDB.getPostDB()
                .whereEqualTo("postDocumentID", postDocumentId)
                .get()
                .await()

            postResponses.add(responses.first().toObject<PostResponse>())
        }
        return postResponses
    }

    override suspend fun getPost(postDocumentID: String): PostResponse {
        val posts = firestoreDB.getPostDB()
            .whereEqualTo("postDocumentID", postDocumentID)
            .get()
            .await()

        return posts.first().toObject<PostResponse>()
    }

    override suspend fun deletePost(userDocumentID: String, postDocumentID: String): Boolean {
        val postDocumentRef = firestoreDB.getPostDB().document(postDocumentID)
        val userRef = firestoreDB.getUsersDB().document(userDocumentID)

        // 포스트 삭제, 사용자 정보에서 해당 포스트 삭제
        return firestoreDB.runTransaction { transaction ->
            transaction.delete(postDocumentRef)
            transaction.update(userRef, "posts", FieldValue.arrayRemove(postDocumentID))
        }.doneSuccessful()
    }

    override fun createPost(imageUrls: List<String>): String {
        val ref = firestoreDB.getPostDB().document()

        firestoreDB.runTransaction { transaction ->
            transaction.set(ref, PostForInit(
                images = imageUrls
            ))
            transaction.update(ref, "postDocumentID", ref.id)
        }

        return ref.id
    }
}