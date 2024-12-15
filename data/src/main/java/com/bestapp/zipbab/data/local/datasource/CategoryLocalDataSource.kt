package com.bestapp.zipbab.data.local.datasource

import com.bestapp.zipbab.data.local.room.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryLocalDataSource {

    suspend fun replaceFlashMeet(category: CategoryEntity)

    suspend fun replaceMeet(category: CategoryEntity)

    fun getCategories(): Flow<List<CategoryEntity>>
}
