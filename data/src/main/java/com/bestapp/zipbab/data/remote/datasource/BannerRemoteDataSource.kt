package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.banner.BannerItemResponse
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result

interface BannerRemoteDataSource {

    suspend fun getHomeBanner(): Result<List<BannerItemResponse>, NetworkError>
}
