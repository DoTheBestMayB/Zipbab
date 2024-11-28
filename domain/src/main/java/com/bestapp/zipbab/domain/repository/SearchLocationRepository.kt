package com.bestapp.zipbab.domain.repository

import androidx.annotation.IntRange
import com.bestapp.zipbab.domain.model.kakaomap.SearchLocation
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result

interface SearchLocationRepository {
    suspend fun convertLocation(
        query: String,
        analyzeType: String = "similar",
        @IntRange(from = 1, to = 45) page: Int = 1,
        @IntRange(from = 1, to = 30) size: Int = 10,
    ): Result<SearchLocation, NetworkError>
}
