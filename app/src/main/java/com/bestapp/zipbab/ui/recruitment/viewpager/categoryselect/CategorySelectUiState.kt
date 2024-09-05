package com.bestapp.zipbab.ui.recruitment.viewpager.categoryselect

import com.bestapp.zipbab.model.FilterUiState

data class CategorySelectUiState(
    val categories: List<FilterUiState.FoodUiState> = emptyList(),
)