package com.bestapp.zipbab.data.repository

import android.graphics.Bitmap
import android.net.Uri
import com.bestapp.zipbab.data.datasource.remote.StorageRemoteDataSource
import com.google.firebase.storage.StorageReference
import javax.inject.Inject


internal class StorageRepositoryImpl @Inject constructor(
    private val storageRemoteDataSource: StorageRemoteDataSource,
): StorageRepository {
    override suspend fun uploadImage(imageUri: Uri): String {
        return storageRemoteDataSource.uploadImage(imageUri)
    }

    override suspend fun uploadImage(imageBitmap: Bitmap): String {
        return storageRemoteDataSource.uploadImage(imageBitmap)
    }

    override suspend fun downloadImage(storageRef: StorageReference): String {
        return storageRemoteDataSource.downloadImage(storageRef)
    }

    override suspend fun deleteImage(profileImage: String) {
        storageRemoteDataSource.deleteImage(profileImage)
    }
}