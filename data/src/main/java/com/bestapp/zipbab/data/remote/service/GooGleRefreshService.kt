package com.bestapp.zipbab.data.remote.service

import retrofit2.http.Body
import retrofit2.http.POST

interface GooGleRefreshService {
    @POST("o/oauth2/v2/auth")
    fun getRefreshTokenInformation(
        @Body refresh: com.bestapp.zipbab.data.remote.notification.refresh.Refresh
    ) : com.bestapp.zipbab.data.remote.notification.refresh.RefreshReturn
}