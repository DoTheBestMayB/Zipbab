package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.LoginResponse
import com.bestapp.zipbab.data.model.remote.Review
import com.bestapp.zipbab.data.model.remote.SignUpResponse
import com.bestapp.zipbab.data.model.remote.UserResponse

interface UserRemoteDataSource {

    suspend fun getUser(userDocumentID: String): UserResponse

    suspend fun login(id: String, pw: String): LoginResponse

    suspend fun signUpUser(nickname: String, email: String, password: String): SignUpResponse

    suspend fun checkSignOutIsNotAllowed(userDocumentID: String): Boolean

    suspend fun signOutUser(userDocumentID: String): Boolean

    suspend fun updateUserNickname(userDocumentID: String, nickname: String): Boolean

    suspend fun updateUserTemperature(reviews: List<Review>): Boolean

    suspend fun updateUserMeetingCount(userDocumentID: String): Boolean

    suspend fun updateProfileImage(userDocumentID: String, imageUri: String): Boolean

    suspend fun addPost(userDocumentID: String, postDocumentId: String): Boolean

    suspend fun updateEmail(userDocumentID: String, email: String): Boolean
}