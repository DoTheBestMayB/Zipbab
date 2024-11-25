package com.bestapp.zipbab.data.remote.datasource

import android.net.Uri
import com.bestapp.zipbab.data.networking.safeFirebaseCall
import com.bestapp.zipbab.data.remote.firestoreDB.FirestoreDB
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class StorageRemoteDataSourceImpl @Inject constructor(
    private val firestoreDB: FirestoreDB,
) : StorageRemoteDataSource {

    private val currentTime: Long
        get() = System.currentTimeMillis()

    override suspend fun uploadImage(pathPrefix: String, imageUri: Uri): Result<String, NetworkError> {
        return safeFirebaseCall<String> {
            val imageRef = firestoreDB.getImagesDB().child("$pathPrefix/${currentTime}.jpg")

            imageRef.putFile(imageUri).await()
            imageRef.path
        }
    }
}
