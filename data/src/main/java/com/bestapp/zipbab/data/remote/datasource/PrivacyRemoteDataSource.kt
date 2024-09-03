package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.Privacy

interface PrivacyRemoteDataSource {

    suspend fun getPrivacyInfo(): Privacy

    suspend fun getLocationPolicyInfo(): Privacy

    suspend fun getDeleteRequestInfo(): Privacy
}