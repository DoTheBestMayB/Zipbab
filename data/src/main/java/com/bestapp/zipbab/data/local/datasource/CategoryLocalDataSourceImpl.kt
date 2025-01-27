package com.bestapp.zipbab.data.local.datasource

import com.bestapp.zipbab.data.local.room.dao.CategoryDao
import com.bestapp.zipbab.data.local.room.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryLocalDataSourceImpl @Inject constructor(
    private val categoryDao: CategoryDao,
): CategoryLocalDataSource {

    override suspend fun replaceFlashMeet(category: CategoryEntity) {
        categoryDao.replaceCategory(category)
    }

    override suspend fun replaceMeet(category: CategoryEntity) {
        categoryDao.replaceCategory(category)
    }

    override fun getCategories(): Flow<List<CategoryEntity>> {
        return categoryDao.getCategory()
    }
}
