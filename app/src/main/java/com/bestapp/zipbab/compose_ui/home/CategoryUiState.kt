package com.bestapp.zipbab.compose_ui.home

import com.bestapp.zipbab.domain.model.category.CategoryGroup

interface CategoryUiState {
    data object Loading: CategoryUiState

    data class Success(
        val categories: List<CategoryGroup>,
    ): CategoryUiState
}
