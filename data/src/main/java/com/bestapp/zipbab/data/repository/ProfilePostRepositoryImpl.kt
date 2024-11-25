package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.remote.datasource.PostRemoteDataSource
import com.bestapp.zipbab.domain.repository.ProfilePostRepository
import javax.inject.Inject

internal class ProfilePostRepositoryImpl @Inject constructor(
    private val postRemoteDataSource: PostRemoteDataSource,
) : ProfilePostRepository {
    override suspend fun deletePost(userDocumentID: String, postDocumentID: String): Boolean {
        return postRemoteDataSource.deletePost(userDocumentID, postDocumentID)
    }

    override suspend fun createPost(imagePaths: List<String>): String {
        return postRemoteDataSource.createPost(imagePaths)
    }
}
