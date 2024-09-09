package com.bestapp.zipbab.data.repository

import kotlinx.coroutines.flow.Flow

interface VerifyRepository {

    suspend fun sendCode(email: String): Boolean

    fun getEmailAuthState(): Flow<Boolean>
}