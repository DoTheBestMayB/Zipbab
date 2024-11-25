package com.bestapp.zipbab.data.remote.datasource

interface PostRemoteDataSource {
    suspend fun deletePost(userDocumentId: String, postDocumentId: String): Boolean
    suspend fun createPost(imagePaths: List<String>): String
}
