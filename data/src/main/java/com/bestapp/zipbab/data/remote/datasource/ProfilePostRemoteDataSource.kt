package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.user.ProfilePostResponse
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result

interface ProfilePostRemoteDataSource {
    suspend fun deletePost(userId: String, postDocumentId: String): Result<Boolean, NetworkError>
    suspend fun getPost(postDocumentId: String): Result<ProfilePostResponse, NetworkError>
    suspend fun createPost(userId: String, imagePaths: List<String>): Result<String, NetworkError>
}
