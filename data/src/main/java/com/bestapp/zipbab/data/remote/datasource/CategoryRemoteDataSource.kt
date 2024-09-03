package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.FilterResponse

interface CategoryRemoteDataSource {

    suspend fun getFoodCategory(): FilterResponse.FoodCategory

    suspend fun getCostCategory(): FilterResponse.CostCategory
}