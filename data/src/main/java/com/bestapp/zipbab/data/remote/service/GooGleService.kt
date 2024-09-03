package com.bestapp.zipbab.data.remote.service

import retrofit2.http.Body
import retrofit2.http.POST

interface GooGleService {
    @POST("token")
    suspend fun getTokenInformation(
        @Body access: com.bestapp.zipbab.data.remote.notification.access.Access
    ) : com.bestapp.zipbab.data.remote.notification.access.AccessReturn
}