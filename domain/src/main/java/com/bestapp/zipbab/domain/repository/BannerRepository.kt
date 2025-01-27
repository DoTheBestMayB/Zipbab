package com.bestapp.zipbab.domain.repository

import com.bestapp.zipbab.domain.model.banner.BannerItem
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result

interface BannerRepository {

    suspend fun getHomeBanner(): Result<List<BannerItem>, NetworkError>
}
