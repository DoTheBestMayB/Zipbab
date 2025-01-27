package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.local.datasource.CategoryLocalDataSource
import com.bestapp.zipbab.data.mapper.toDomain
import com.bestapp.zipbab.data.mapper.toEntity
import com.bestapp.zipbab.data.model.local.CategoryType
import com.bestapp.zipbab.data.remote.datasource.CategoryRemoteDataSource
import com.bestapp.zipbab.domain.model.category.CategoryGroup
import com.bestapp.zipbab.domain.repository.CategoryRepository
import com.bestapp.zipbab.domain.util.map
import com.bestapp.zipbab.domain.util.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class CategoryRepositoryImpl @Inject constructor(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val categoryLocalDataSource: CategoryLocalDataSource,
) : CategoryRepository {
    override suspend fun fetchCategory() {
        for (type in CategoryType.entries) {
            categoryRemoteDataSource.getCategory(type.serverName).map {
                it.toEntity(type.symbol)
            }.onSuccess {
                categoryLocalDataSource.replaceFlashMeet(it)
            }
        }
    }

    override fun getCategory(): Flow<List<CategoryGroup>> {
        return categoryLocalDataSource.getCategories()
            .map { entities ->
                entities.map {
                    it.toDomain()
                }
            }.distinctUntilChanged()
    }
}
