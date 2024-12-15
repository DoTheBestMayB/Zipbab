package com.bestapp.zipbab.domain.repository

import com.bestapp.zipbab.domain.model.category.CategoryGroup
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun fetchCategory()

    fun getCategory(): Flow<List<CategoryGroup>>
}
