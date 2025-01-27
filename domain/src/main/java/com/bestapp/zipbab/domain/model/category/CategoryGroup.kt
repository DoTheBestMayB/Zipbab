package com.bestapp.zipbab.domain.model.category

data class CategoryGroup(
    val name: String,
    val state: CategoryState,
    val icons: List<CategoryIcon> = emptyList(),
)
