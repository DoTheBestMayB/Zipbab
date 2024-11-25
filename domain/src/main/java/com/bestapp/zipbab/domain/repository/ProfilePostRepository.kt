package com.bestapp.zipbab.domain.repository

interface ProfilePostRepository {
    suspend fun deletePost(userDocumentID: String, postDocumentID: String): Boolean
    suspend fun createPost(imagePaths: List<String>): String
}
