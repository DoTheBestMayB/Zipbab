package com.bestapp.zipbab.data.repository

interface ProviderRepository {
    suspend fun getTokenInfo(id: String, secret: String, code: String, token: String, type: String) : com.bestapp.zipbab.data.remote.notification.access.AccessReturn

    suspend fun getRefreshInfo(id: String, secret: String, code: String, type: String, uri: String) : com.bestapp.zipbab.data.remote.notification.refresh.RefreshReturn
}