package com.bestapp.zipbab.data.mapper

import com.bestapp.zipbab.data.model.remote.category.FoodCategoryResponse
import com.bestapp.zipbab.data.model.remote.category.FoodIconResponse
import com.bestapp.zipbab.domain.model.category.FoodCategory
import com.bestapp.zipbab.domain.model.category.FoodIcon

fun FoodCategoryResponse.toDomain(): FoodCategory {
    return FoodCategory(
        icons = icons.map { it.toDomain() },
    )
}

fun FoodIconResponse.toDomain(): FoodIcon {
    return FoodIcon(
        imageUrl = imageUrl,
        name = name,
    )
}
