package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.local.datasource.UserPrivateLocalDataSource
import com.bestapp.zipbab.data.mapper.toDomain
import com.bestapp.zipbab.data.mapper.toEntity
import com.bestapp.zipbab.data.remote.datasource.PrivacyRemoteDataSource
import com.bestapp.zipbab.domain.model.TermsAndCondition
import com.bestapp.zipbab.domain.model.user.UserPrivate
import com.bestapp.zipbab.domain.repository.AppSettingRepository
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import com.bestapp.zipbab.domain.util.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


internal class AppSettingRepositoryImpl @Inject constructor(
    private val userPrivateLocalDataSource: UserPrivateLocalDataSource,
    private val privacyRemoteDataSource: PrivacyRemoteDataSource,
) : AppSettingRepository {

    override val userPrivateData: Flow<UserPrivate?> = userPrivateLocalDataSource.privateData.map {
        it?.toDomain()
    }

    override suspend fun updatePrivateData(userPrivate: UserPrivate) {
        return userPrivateLocalDataSource.updatePrivateData(userPrivate.toEntity())
    }

    override suspend fun removePrivateData() {
        return userPrivateLocalDataSource.removePrivateData()
    }

    override suspend fun getRememberId(): String {
        return userPrivateLocalDataSource.getRememberId()
    }

    override suspend fun updateRememberId(id: String): Boolean {
        return userPrivateLocalDataSource.updateRememberId(id)
    }

    override suspend fun removeRememberId(): Boolean {
        return userPrivateLocalDataSource.removeRememberId()
    }

    override suspend fun getPrivacyPolicy(): Result<TermsAndCondition, NetworkError> {
        return privacyRemoteDataSource.getPrivacyPolicy().map {
            it.toDomain()
        }
    }

    override suspend fun getLocationPolicy(): Result<TermsAndCondition, NetworkError> {
        return privacyRemoteDataSource.getLocationPolicy().map {
            it.toDomain()
        }
    }

    override suspend fun getDeleteRequestUrl(): Result<String, NetworkError> {
        return privacyRemoteDataSource.getDeleteRequestUrl()
    }
}
