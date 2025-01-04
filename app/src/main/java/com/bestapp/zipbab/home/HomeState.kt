package com.bestapp.zipbab.home

import com.bestapp.zipbab.domain.model.category.CategoryGroup

data class HomeState(
    val isLoading: Boolean = true,
    val isLoggedIn: Boolean = false,
    val isAlertExist: Boolean = false,
    val announcementText: String = "",
    val announcementId: String = "",
    val categories: List<CategoryGroup> = emptyList(),
)
