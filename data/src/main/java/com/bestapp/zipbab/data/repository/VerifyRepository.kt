package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.model.remote.VerifyStateEntity
import kotlinx.coroutines.flow.Flow

interface VerifyRepository {

    suspend fun sendCode(email: String, password: String): VerifyStateEntity

    fun getEmailAuthState(email: String): Flow<Boolean>

    fun resetAuthState()

    suspend fun renewAuthState(email: String, password: String)

    suspend fun removeAuth(password: String): Boolean
}