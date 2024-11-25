package com.bestapp.zipbab.domain.repository

import com.bestapp.zipbab.domain.model.category.FoodCategory
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result

interface CategoryRepository {
    suspend fun getFoodCategory(): Result<FoodCategory, NetworkError>
}
