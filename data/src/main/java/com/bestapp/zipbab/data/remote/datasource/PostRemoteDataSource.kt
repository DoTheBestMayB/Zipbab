package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.PostResponse

interface PostRemoteDataSource {

    suspend fun getPosts(userDocumentID: String): List<PostResponse>
    suspend fun getPost(postDocumentID: String): PostResponse
    suspend fun deletePost(userDocumentID: String, postDocumentID: String): Boolean
    fun createPost(imageUrls: List<String>): String
}