package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.remote.di.NetworkModule
import com.bestapp.zipbab.data.remote.service.GooGleRefreshService
import com.bestapp.zipbab.data.remote.service.GooGleService
import javax.inject.Inject

internal class ProviderRepositoryImpl @Inject constructor(
    @NetworkModule.GoogleTokenProvider private val gooGleService: GooGleService,
    @NetworkModule.GoogleRefreshTokenProvider private val gooGleRefreshService: GooGleRefreshService
) : ProviderRepository {
    override suspend fun getTokenInfo( //accesstoken만료시 호출
        id: String,
        secret: String,
        code: String,
        uri: String,
        type: String
    ): com.bestapp.zipbab.data.remote.notification.access.AccessReturn {
        return gooGleService.getTokenInformation(
            com.bestapp.zipbab.data.remote.notification.access.Access(
                id,
                secret,
                code,
                uri,
                type
            )
        )
    }

    override suspend fun getRefreshInfo( //refresh토큰 만료시 호출
        id: String,
        secret: String,
        code: String,
        type: String,
        uri: String
    ): com.bestapp.zipbab.data.remote.notification.refresh.RefreshReturn {
        return gooGleRefreshService.getRefreshTokenInformation(
            com.bestapp.zipbab.data.remote.notification.refresh.Refresh(
                id,
                secret,
                code,
                type,
                uri
            )
        )
    }
}