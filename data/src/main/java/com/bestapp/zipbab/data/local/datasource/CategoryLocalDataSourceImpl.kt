package com.bestapp.zipbab.data.local.datasource

import com.bestapp.zipbab.data.local.room.dao.FlashMeetCategoryDao
import com.bestapp.zipbab.data.local.room.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryLocalDataSourceImpl @Inject constructor(
    private val flashMeetCategoryDao: FlashMeetCategoryDao,
): CategoryLocalDataSource {

    override suspend fun replaceFlashMeet(categories: List<CategoryEntity>) {
        flashMeetCategoryDao.replaceCategory(categories)
    }

    override fun getFlashMeet(): Flow<List<CategoryEntity>> {
        return flashMeetCategoryDao.getCategory()
    }
}
