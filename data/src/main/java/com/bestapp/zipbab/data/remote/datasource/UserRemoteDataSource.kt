package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.user.UserResponse
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result

interface UserRemoteDataSource {

    suspend fun getUser(userId: String): Result<UserResponse, NetworkError>

    suspend fun updateUserNickname(userId: String, nickname: String): Result<Boolean, NetworkError>

    suspend fun updateUserProfileImage(userId: String, imagePath: String): Result<Boolean, NetworkError>

    suspend fun addPost(userId: String, postDocumentId: String): Result<Boolean, NetworkError>

    suspend fun removePost(userId: String, postDocumentId: String): Result<Boolean, NetworkError>
}
