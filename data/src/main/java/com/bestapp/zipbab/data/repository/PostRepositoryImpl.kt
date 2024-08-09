package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.datasource.remote.PostRemoteDataSource
import com.bestapp.zipbab.data.model.remote.PostResponse
import javax.inject.Inject

internal class PostRepositoryImpl @Inject constructor(
    private val postRemoteDataSource: PostRemoteDataSource,
    private val storageRepository: StorageRepository,
) : PostRepository {
    override suspend fun getPosts(userDocumentID: String): List<PostResponse> {
        return postRemoteDataSource.getPosts(userDocumentID)
    }

    override suspend fun getPost(postDocumentID: String): PostResponse {
        return postRemoteDataSource.getPost(postDocumentID)
    }

    override suspend fun deletePost(userDocumentID: String, postDocumentID: String): Boolean {
        val isSuccess = postRemoteDataSource.deletePost(userDocumentID, postDocumentID)
        if (!isSuccess) {
            return false
        }

        // 포스트에 사용된 이미지 삭제
        val post = postRemoteDataSource.getPost(postDocumentID)
        for (image in post.images) {
            storageRepository.deleteImage(image)
        }
        return true
    }
}