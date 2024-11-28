package com.bestapp.zipbab.domain.repository

import com.bestapp.zipbab.domain.model.UploadState
import com.bestapp.zipbab.domain.model.user.User
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface UserRepository {

    suspend fun getUser(userId: String): Result<User, NetworkError>

    suspend fun updateUserNickname(userId: String, nickname: String): Result<Boolean, NetworkError>

    // TODO : 사용자 평가 시스템 구현 시 필요
//    suspend fun updateUserTemperature(reviews: List<Review>): Result<Boolean, NetworkError>

    suspend fun updateUserProfileImage(
        userId: String,
        profileImageUri: String
    ): Result<Boolean, NetworkError>

    fun addPostWithWorkManager(
        workRequestKey: UUID,
        userDocumentID: String,
        tempPostDocumentID: String,
        images: List<String>
    ): Flow<UploadState>
}
