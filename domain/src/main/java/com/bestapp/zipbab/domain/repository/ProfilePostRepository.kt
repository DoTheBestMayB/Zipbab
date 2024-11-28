package com.bestapp.zipbab.domain.repository

import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result

interface ProfilePostRepository {
    suspend fun deletePost(userId: String, postDocumentId: String): Result<Boolean, NetworkError>
    suspend fun createPost(userId: String, imagePaths: List<String>): Result<String, NetworkError>
}
