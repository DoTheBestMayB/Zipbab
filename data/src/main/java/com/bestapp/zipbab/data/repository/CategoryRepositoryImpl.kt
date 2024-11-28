package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.mapper.toDomain
import com.bestapp.zipbab.data.remote.datasource.CategoryRemoteDataSource
import com.bestapp.zipbab.domain.model.category.FoodCategory
import com.bestapp.zipbab.domain.repository.CategoryRepository
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import com.bestapp.zipbab.domain.util.map
import javax.inject.Inject

internal class CategoryRepositoryImpl @Inject constructor(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
) : CategoryRepository {
    override suspend fun getFoodCategory(): Result<FoodCategory, NetworkError> {
        return categoryRemoteDataSource.getFoodCategory().map {
            it.toDomain()
        }
    }
}
