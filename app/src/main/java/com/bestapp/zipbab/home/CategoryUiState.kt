package com.bestapp.zipbab.home

import com.bestapp.zipbab.domain.model.category.CategoryGroup

interface CategoryUiState {
    data object Loading: CategoryUiState

    data class Success(
        val categories: CategoryGroup,
    ): CategoryUiState
}
