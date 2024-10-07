package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.model.remote.VerifyStateEntity
import com.bestapp.zipbab.data.remote.datasource.VerifyRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VerifyRepositoryImpl @Inject constructor(
    private val verifyRemoteDataSource: VerifyRemoteDataSource,
): VerifyRepository {

    override suspend fun sendCode(email: String, password: String): VerifyStateEntity {
        return verifyRemoteDataSource.sendCodeToEmail(email, password)
    }

    override fun getEmailAuthState(email: String): Flow<Boolean> {
        return verifyRemoteDataSource.getEmailAuthState(email)
    }

    override fun resetAuthState() {
        verifyRemoteDataSource.resetAuthState()
    }

    override suspend fun renewAuthState(email: String, password: String) {
        verifyRemoteDataSource.renewAuthState(email, password)
    }

    override suspend fun removeAuth(password: String): Boolean {
        return verifyRemoteDataSource.removeAuth(password)
    }
}