package com.bestapp.zipbab.data.remote.datasource

interface VerifyRemoteDataSource {

    suspend fun sendCode(email: String): Boolean
}