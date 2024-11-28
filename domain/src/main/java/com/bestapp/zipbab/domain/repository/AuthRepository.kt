package com.bestapp.zipbab.domain.repository

import com.bestapp.zipbab.domain.model.auth.AuthState
import com.bestapp.zipbab.domain.model.user.SignUp
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(id: String, pw: String): Result<Boolean, NetworkError>

    suspend fun signUpUser(signUp: SignUp): Result<AuthState, NetworkError>

    suspend fun signOutUser(userId: String): Result<Boolean, NetworkError>

    suspend fun sendCode(email: String, password: String): Result<AuthState, NetworkError>

    fun getEmailAuthState(email: String): Flow<Boolean>

    fun resetAuthState()

    suspend fun renewAuthState(email: String, password: String)

    suspend fun removeAuth(password: String): Result<Boolean, NetworkError>
}
