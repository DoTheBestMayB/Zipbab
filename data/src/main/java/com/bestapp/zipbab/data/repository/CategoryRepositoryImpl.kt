package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.datasource.remote.CategoryRemoteDataSource
import com.bestapp.zipbab.data.model.remote.FilterResponse
import javax.inject.Inject

internal class CategoryRepositoryImpl @Inject constructor(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
) : CategoryRepository {
    override suspend fun getFoodCategory(): FilterResponse.FoodCategory {
        return categoryRemoteDataSource.getFoodCategory()
    }

    override suspend fun getCostCategory(): FilterResponse.CostCategory {
        return categoryRemoteDataSource.getCostCategory()
    }
}