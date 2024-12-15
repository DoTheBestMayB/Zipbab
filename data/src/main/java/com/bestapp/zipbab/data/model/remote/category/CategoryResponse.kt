package com.bestapp.zipbab.data.model.remote.category

data class CategoryResponse(
    val icons: List<IconResponse> = emptyList(),
    val state: CategoryState,
)

enum class CategoryState {
    READY, AVAILABLE
}
