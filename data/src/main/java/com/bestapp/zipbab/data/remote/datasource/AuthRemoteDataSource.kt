package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.auth.AuthStateDto
import com.bestapp.zipbab.data.model.remote.auth.SignUpRequest
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface AuthRemoteDataSource {

    suspend fun login(id: String, pw: String): Result<Boolean, NetworkError>

    suspend fun signUpUser(signUpRequest: SignUpRequest): Result<AuthStateDto, NetworkError>

    suspend fun checkSignOutIsNotAllowed(userId: String): Result<Boolean, NetworkError>

    suspend fun signOutUser(userId: String): Result<Boolean, NetworkError>

    suspend fun sendCodeToEmail(email: String, password: String): Result<AuthStateDto, NetworkError>

    fun resetAuthState()

    fun getEmailAuthState(email: String): Flow<Boolean>

    suspend fun renewAuthState(email: String, password: String)

    suspend fun removeAuth(password: String): Result<Boolean, NetworkError>
}
