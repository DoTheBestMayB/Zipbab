package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.model.remote.Privacy
import kotlinx.coroutines.flow.Flow

interface AppSettingRepository {

    val userDocumentID: Flow<String>

    suspend fun updateUserDocumentId(userDocumentID: String): Boolean

    suspend fun removeUserDocumentId(): Boolean

    suspend fun getRememberId(): String

    suspend fun updateRememberId(id: String): Boolean

    suspend fun removeRememberId(): Boolean

    suspend fun getPrivacyInfo(): Privacy

    suspend fun getLocationPolicyInfo(): Privacy

    suspend fun getDeleteRequestInfo(): Privacy
}