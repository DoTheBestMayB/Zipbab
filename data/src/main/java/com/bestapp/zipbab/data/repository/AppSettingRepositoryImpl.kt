package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.datasource.local.UserLocalDataSource
import com.bestapp.zipbab.data.datasource.remote.PrivacyRemoteDataSource
import com.bestapp.zipbab.data.model.remote.Privacy
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


internal class AppSettingRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val privacyRemoteDataSource: PrivacyRemoteDataSource,
) : AppSettingRepository {

    override val userDocumentID: Flow<String> = userLocalDataSource.userDocumentID

    override suspend fun updateUserDocumentId(userDocumentID: String): Boolean {
        return userLocalDataSource.updateUserDocumentId(userDocumentID)
    }

    override suspend fun removeUserDocumentId(): Boolean {
        return userLocalDataSource.removeUserDocumentId()
    }

    override suspend fun getRememberId(): String {
        return userLocalDataSource.getRememberId()
    }

    override suspend fun updateRememberId(id: String): Boolean {
        return userLocalDataSource.updateRememberId(id)
    }

    override suspend fun removeRememberId(): Boolean {
        return userLocalDataSource.removeRememberId()
    }

    override suspend fun getPrivacyInfo(): Privacy {
        return privacyRemoteDataSource.getPrivacyInfo()
    }

    override suspend fun getLocationPolicyInfo(): Privacy {
        return privacyRemoteDataSource.getLocationPolicyInfo()
    }

    override suspend fun getDeleteRequestInfo(): Privacy {
        return privacyRemoteDataSource.getDeleteRequestInfo()
    }
}