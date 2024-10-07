package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.VerifyStateEntity
import kotlinx.coroutines.flow.Flow

interface VerifyRemoteDataSource {

    suspend fun sendCodeToEmail(email: String, password: String): VerifyStateEntity

    fun resetAuthState()

    fun getEmailAuthState(email: String): Flow<Boolean>

    suspend fun renewAuthState(email: String, password: String)

    suspend fun removeAuth(password: String): Boolean
}