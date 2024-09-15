package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.model.UploadStateEntity
import com.bestapp.zipbab.data.model.local.SignOutEntity
import com.bestapp.zipbab.data.model.remote.LoginResponse
import com.bestapp.zipbab.data.model.remote.NotificationType
import com.bestapp.zipbab.data.model.remote.NotificationTypeResponse
import com.bestapp.zipbab.data.model.remote.Review
import com.bestapp.zipbab.data.model.remote.SignUpResponse
import com.bestapp.zipbab.data.model.remote.UserResponse
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface UserRepository {
    suspend fun getUser(userDocumentID: String): UserResponse
    suspend fun login(id: String, pw: String): LoginResponse
    suspend fun signUpUser(nickname: String, email: String, password: String): SignUpResponse
    suspend fun signOutUser(userDocumentID: String): SignOutEntity
    suspend fun updateUserNickname(userDocumentID: String, nickname: String): Boolean
    suspend fun updateUserTemperature(reviews: List<Review>): Boolean
    suspend fun updateUserMeetingCount(userDocumentID: String): Boolean
    suspend fun updateUserProfileImage(userDocumentID: String, profileImageUri: String?): Boolean
    suspend fun deleteUserProfileImage(userDocumentID: String)
    suspend fun updateEmail(userDocumentID: String, email: String): Boolean
    suspend fun getAccessToken(): com.bestapp.zipbab.data.remote.notification.fcm.AccessToken

    suspend fun removeItem(
        udi: String,
        exchange: List<NotificationTypeResponse>,
        index: Int
    ): Boolean

    fun addPostWithAsync(
        userDocumentID: String,
        tempPostDocumentID: String,
        images: List<String>
    ): Flow<UploadStateEntity>

    fun addPostWithWorkManager(
        workRequestKey: UUID,
        userDocumentID: String,
        tempPostDocumentID: String,
        images: List<String>
    ): Flow<UploadStateEntity>

    suspend fun addNotification(
        type: NotificationType,
        userDocumentID: String,
        meetingDocumentID: String,
        hostDocumentID: String
    ): Boolean
}