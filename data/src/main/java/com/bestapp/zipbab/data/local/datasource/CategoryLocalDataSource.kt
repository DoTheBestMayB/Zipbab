package com.bestapp.zipbab.data.local.datasource

import com.bestapp.zipbab.data.local.room.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryLocalDataSource {

    suspend fun replaceFlashMeet(categories: List<CategoryEntity>)

    fun getFlashMeet(): Flow<List<CategoryEntity>>
}
