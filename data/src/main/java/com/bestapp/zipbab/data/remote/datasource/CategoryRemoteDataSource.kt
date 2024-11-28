package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.category.FoodCategoryResponse
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result

interface CategoryRemoteDataSource {

    suspend fun getFoodCategory(): Result<FoodCategoryResponse, NetworkError>
}
