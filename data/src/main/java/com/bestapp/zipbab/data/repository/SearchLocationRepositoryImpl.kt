package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.mapper.toDomain
import com.bestapp.zipbab.data.model.remote.kakaomap.SearchLocationResponse
import com.bestapp.zipbab.data.networking.safeCall
import com.bestapp.zipbab.data.remote.service.SearchLocationService
import com.bestapp.zipbab.domain.model.kakaomap.SearchLocation
import com.bestapp.zipbab.domain.repository.SearchLocationRepository
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import com.bestapp.zipbab.domain.util.map
import javax.inject.Inject

internal class SearchLocationRepositoryImpl @Inject constructor(
    private val searchLocationService: SearchLocationService
) : SearchLocationRepository {
    override suspend fun convertLocation(
        query: String,
        analyzeType: String,
        page: Int,
        size: Int,
    ): Result<SearchLocation, NetworkError> {
        return safeCall<SearchLocationResponse> {
            searchLocationService.convertLocation(
                query,
                analyzeType,
                page,
                size,
            )
        }.map { response ->
            response.toDomain()
        }
    }
}
