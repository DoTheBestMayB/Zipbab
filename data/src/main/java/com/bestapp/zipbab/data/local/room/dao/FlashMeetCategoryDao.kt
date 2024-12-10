package com.bestapp.zipbab.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.bestapp.zipbab.data.local.room.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashMeetCategoryDao {

    @Upsert
    suspend fun upsertCategory(categories: List<CategoryEntity>)

    @Query("DELETE FROM CategoryEntity WHERE name NOT IN (:categoryNames)")
    suspend fun deleteCategoryNotIn(categoryNames: List<String>)

    @Transaction
    suspend fun replaceCategory(categories: List<CategoryEntity>) {
        val categoryNames = categories.map { it.name }

        deleteCategoryNotIn(categoryNames)
        upsertCategory(categories)
    }

    @Query("SELECT * FROM CategoryEntity")
    fun getCategory(): Flow<List<CategoryEntity>>
}
