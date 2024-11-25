package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.user.ProfilePostRequest
import com.bestapp.zipbab.data.model.remote.user.ProfilePostResponse
import com.bestapp.zipbab.data.remote.firestoreDB.FirestoreDB
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class PostRemoteDataSourceImpl @Inject constructor(
    private val firestoreDB: FirestoreDB,
) : PostRemoteDataSource {

    override suspend fun deletePost(userDocumentId: String, postDocumentId: String): Boolean {
        val postDocumentRef = firestoreDB.getPostDB().document(postDocumentId)
        val userRef = firestoreDB.getUsersDB().document(userDocumentId)

        val profilePost =
            postDocumentRef.get().await().toObject<ProfilePostResponse>() ?: return false

        // 포스트 삭제, 사용자 정보에서 해당 포스트 삭제
        firestoreDB.runTransaction { transaction ->
            transaction.delete(postDocumentRef)
            transaction.update(userRef, "posts", FieldValue.arrayRemove(postDocumentId))
        }.await()

        // 포스트 이미지 삭제
        try {
            coroutineScope {
                for (path in profilePost.images) {
                    launch {
                        firestoreDB.getStorageRef(path).delete().addOnFailureListener {
                            // TODO : 수동으로 파일을 지울 수 있도록 firebase crashlytics 기록 필요
//                            Firebase.crashlytics.log("Failed to delete image: $path")
//                            Firebase.crashlytics.recordException(e)
                        }.await()
                    }
                }
            }
        } catch (e: Exception) {
            // TODO : 수동으로 파일을 지울 수 있도록 firebase crashlytics 기록 필요
//            Firebase.crashlytics.log("Failed to delete image: $path")
//            Firebase.crashlytics.recordException(e)
        }
        return true
    }

    override suspend fun createPost(imagePaths: List<String>): String {
        return firestoreDB.runTransaction { transaction ->
            val ref = firestoreDB.getPostDB().document()
            transaction.set(ref, ProfilePostRequest(imagePaths))
            ref.id
        }.await()
    }
}
