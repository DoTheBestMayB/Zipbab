package com.bestapp.zipbab.data.mapper

import com.bestapp.zipbab.data.local.room.entity.CategoryEntity
import com.bestapp.zipbab.data.local.room.entity.Icon
import com.bestapp.zipbab.data.model.remote.category.CategoryResponse
import com.bestapp.zipbab.data.model.remote.category.CategoryState
import com.bestapp.zipbab.data.model.remote.category.IconResponse
import com.bestapp.zipbab.domain.model.category.CategoryGroup
import com.bestapp.zipbab.domain.model.category.CategoryIcon

fun CategoryResponse.toDomain(name: String): CategoryGroup {
    return CategoryGroup(
        name = name,
        state = state.toDomain(),
        icons = icons.map { it.toDomain() },
    )
}

fun CategoryState.toDomain(): com.bestapp.zipbab.domain.model.category.CategoryState {
    return when (this) {
        CategoryState.READY -> com.bestapp.zipbab.domain.model.category.CategoryState.READY
        CategoryState.AVAILABLE -> com.bestapp.zipbab.domain.model.category.CategoryState.AVAILABLE
    }
}

fun IconResponse.toDomain(): CategoryIcon {
    return CategoryIcon(
        imageUrl = imageUrl,
        label = name,
    )
}

fun CategoryResponse.toEntity(name: String): CategoryEntity {
    return CategoryEntity(
        name = name,
        icons = icons.map { it.toEntity() },
        state = state.name,
    )
}

fun CategoryEntity.toDomain(): CategoryGroup {
    return CategoryGroup(
        name = name,
        state = state.toDomainCategoryState(),
        icons = icons.map { it.toDomain() }
    )
}

fun String.toDomainCategoryState(): com.bestapp.zipbab.domain.model.category.CategoryState {
    return com.bestapp.zipbab.domain.model.category.CategoryState.entries.firstOrNull { it.name == this }
        ?: com.bestapp.zipbab.domain.model.category.CategoryState.READY
}

fun IconResponse.toEntity(): Icon {
    return Icon(
        imageUrl = imageUrl,
        name = name,
    )
}

fun Icon.toDomain(): CategoryIcon {
    return CategoryIcon(
        imageUrl = imageUrl,
        label = name,
    )
}
