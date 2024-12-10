package com.bestapp.zipbab.data.mapper

import com.bestapp.zipbab.data.local.room.entity.CategoryEntity
import com.bestapp.zipbab.data.model.remote.category.CategoryResponse
import com.bestapp.zipbab.data.model.remote.category.IconResponse
import com.bestapp.zipbab.domain.model.category.CategoryGroup
import com.bestapp.zipbab.domain.model.category.CategoryIcon

fun CategoryResponse.toDomain(): CategoryGroup {
    return CategoryGroup(
        icons = icons.map { it.toDomain() },
    )
}

fun IconResponse.toDomain(): CategoryIcon {
    return CategoryIcon(
        imageUrl = imageUrl,
        label = name,
    )
}

fun CategoryResponse.toEntity(): List<CategoryEntity> {
    return icons.map {
        CategoryEntity(
            name = it.name,
            imageUrl = it.imageUrl,
        )
    }
}

fun CategoryEntity.toDomain(): CategoryIcon {
    return CategoryIcon(
        imageUrl = imageUrl,
        label = name,
    )
}
