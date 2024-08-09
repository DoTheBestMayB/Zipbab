package com.bestapp.zipbab.data.datasource.remote

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.storage.StorageReference

interface StorageRemoteDataSource {

    suspend fun uploadImage(imageUri: Uri): String
    suspend fun uploadImage(imageBitmap: Bitmap): String
    suspend fun downloadImage(storageRef: StorageReference): String
    suspend fun deleteImage(profileImage: String)
}