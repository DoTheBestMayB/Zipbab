package com.bestapp.zipbab.home

import com.bestapp.zipbab.domain.model.category.CategoryGroup

interface HomeAction {

    data object OnSearchClick: HomeAction
    data object OnAlertClick: HomeAction
    data object OnAnnouncementNotificationClick: HomeAction
    data class OnCategoryClick(val categoryGroup: CategoryGroup): HomeAction
    data object OnCategoryCreateClick: HomeAction
}
