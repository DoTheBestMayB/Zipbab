package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.term.TermsAndConditionResponse
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result

interface PrivacyRemoteDataSource {

    suspend fun getPrivacyPolicy(): Result<TermsAndConditionResponse, NetworkError>

    suspend fun getLocationPolicy(): Result<TermsAndConditionResponse, NetworkError>

    suspend fun getDeleteRequestUrl(): Result<String, NetworkError>
}
