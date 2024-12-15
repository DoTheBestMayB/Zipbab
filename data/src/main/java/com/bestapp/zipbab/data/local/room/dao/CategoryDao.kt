package com.bestapp.zipbab.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.bestapp.zipbab.data.local.room.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Upsert
    suspend fun upsertCategory(category: CategoryEntity)

    @Query("SELECT * FROM CategoryEntity WHERE name = :name LIMIT 1")
    suspend fun getCategoryByName(name: String): CategoryEntity?

    @Transaction
    suspend fun replaceCategory(category: CategoryEntity) {
        val existingCategory = getCategoryByName(category.name)

        if (existingCategory == null || existingCategory.state != category.state || existingCategory.icons != category.icons) {
            upsertCategory(category)
        }
    }

    @Query("SELECT * FROM CategoryEntity")
    fun getCategory(): Flow<List<CategoryEntity>>
}
