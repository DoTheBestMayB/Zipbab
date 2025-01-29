package com.bestapp.zipbab.compose_ui.home

interface HomeAction {

    data object OnSearchClick: HomeAction
    data object OnAlertClick: HomeAction
    data class OnAnnouncementNotificationClick(val eventId: String): HomeAction
    data class OnCategoryClick(val label: String): HomeAction
    data object OnCategoryCreateClick: HomeAction
    data class OnBannerClick(val contentUrl: String): HomeAction
}
