package com.bestapp.zipbab.domain.repository

import com.bestapp.zipbab.domain.model.TermsAndCondition
import com.bestapp.zipbab.domain.model.user.UserPrivate
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface AppSettingRepository {

    val userPrivateData: Flow<UserPrivate?>

    suspend fun updatePrivateData(userPrivate: UserPrivate)

    suspend fun removePrivateData()

    suspend fun getRememberId(): String

    suspend fun updateRememberId(id: String): Boolean

    suspend fun removeRememberId(): Boolean

    suspend fun getPrivacyPolicy(): Result<TermsAndCondition, NetworkError>

    suspend fun getLocationPolicy(): Result<TermsAndCondition, NetworkError>

    suspend fun getDeleteRequestUrl(): Result<String, NetworkError>
}
