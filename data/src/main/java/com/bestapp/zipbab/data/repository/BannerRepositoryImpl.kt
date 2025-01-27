package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.mapper.toDomain
import com.bestapp.zipbab.data.remote.datasource.BannerRemoteDataSource
import com.bestapp.zipbab.domain.model.banner.BannerItem
import com.bestapp.zipbab.domain.repository.BannerRepository
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import com.bestapp.zipbab.domain.util.map
import javax.inject.Inject

class BannerRepositoryImpl @Inject constructor(
    private val bannerRemoteDatasource: BannerRemoteDataSource,
) : BannerRepository {

    override suspend fun getHomeBanner(): Result<List<BannerItem>, NetworkError> {
        return bannerRemoteDatasource.getHomeBanner()
            .map { responses ->
                responses.map {
                    it.toDomain()
                }
            }
    }
}
