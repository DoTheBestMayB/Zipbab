package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.model.remote.FilterResponse

interface CategoryRepository {
    suspend fun getFoodCategory(): FilterResponse.FoodCategory

    suspend fun getCostCategory(): FilterResponse.CostCategory
}