package com.bestapp.zipbab.data.datasource.local

import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {

    val userDocumentID: Flow<String>

    suspend fun updateUserDocumentId(userDocumentID: String): Boolean

    suspend fun removeUserDocumentId(): Boolean

    suspend fun getRememberId(): String

    suspend fun updateRememberId(id: String): Boolean

    suspend fun removeRememberId(): Boolean
}