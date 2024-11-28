package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.user.UserPrivateResponse
import com.bestapp.zipbab.data.networking.safeFirebaseCall
import com.bestapp.zipbab.data.remote.firestoreDB.FirestoreDB
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserPrivateRemoteDataSourceImpl @Inject constructor(
    private val firestoreDB: FirestoreDB,
) : UserPrivateRemoteDataSource {

    override suspend fun getUser(userId: String): Result<UserPrivateResponse, NetworkError> {
        return safeFirebaseCall {
            val querySnapshot = firestoreDB.getUserPrivateDB()
                .whereEqualTo("id", userId)
                .get()
                .await()
            val document =
                querySnapshot.documents.firstOrNull() ?: return Result.Error(NetworkError.NOT_FOUND)

            document.toObject<UserPrivateResponse>()
        }
    }
}
