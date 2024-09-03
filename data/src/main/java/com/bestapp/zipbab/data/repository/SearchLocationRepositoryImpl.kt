package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.model.remote.kakaomap.SearchLocationResponse
import com.bestapp.zipbab.data.remote.service.SearchLocationService
import javax.inject.Inject

internal class SearchLocationRepositoryImpl @Inject constructor(
    private val searchLocationService: SearchLocationService
) : SearchLocationRepository {
    override suspend fun convertLocation(
        query: String,
        analyzeType: String,
        page: Int,
        size: Int,
    ): SearchLocationResponse {
        return searchLocationService.convertLocation(
            query,
            analyzeType,
            page,
            size,
        )
    }
}