package com.bestapp.zipbab

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bestapp.zipbab.compose_ui.alert.AlertScreenRoot
import com.bestapp.zipbab.compose_ui.announcement.AnnouncementScreenRoot
import com.bestapp.zipbab.compose_ui.banner.BannerScreenRoot
import com.bestapp.zipbab.compose_ui.category.CategoryScreenRoot
import com.bestapp.zipbab.compose_ui.home.HomeScreenRoot
import com.bestapp.zipbab.compose_ui.login.LoginScreenRoot
import com.bestapp.zipbab.compose_ui.recruitment.RecruitmentScreenRoot
import com.bestapp.zipbab.compose_ui.register.RegisterScreenRoot
import com.bestapp.zipbab.compose_ui.search_meet.SearchMeetScreenRoot
import com.bestapp.zipbab.compose_ui.setting.SettingScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean,
    modifier: Modifier = Modifier,
) {
    NavHost(
        startDestination = "main",
        navController = navController,
    ) {
        mainGraph(navController, isLoggedIn, modifier)
        settingGraph(navController, isLoggedIn, modifier)
        authGraph(navController, modifier)
    }
}

private fun NavGraphBuilder.mainGraph(
    navController: NavController,
    isLoggedIn: Boolean,
    modifier: Modifier = Modifier,
) {
    navigation(
        startDestination = "home",
        route = "main"
    ) {
        composable(route = "home") {
            HomeScreenRoot(
                onSearchClick = {
                    navController.navigate("search")
                },
                onAlertClick = {
                    if (isLoggedIn) {
                        navController.navigate("alert")
                    } else {
                        navController.navigate("auth")
                    }
                },
                onAnnouncementNotificationClick = {
                    navController.navigate("announcement")
                },
                onCategoryItemClick = {
                    navController.navigate("category")
                },
                onCategoryCreateClick = {
                    navController.navigate("recruitment")
                },
                onBannerClick = {
                    navController.navigate("banner")
                },
                modifier = modifier,
            )
        }
        composable(route = "alert") {
            AlertScreenRoot(
                modifier = modifier,
            )
        }
        composable(route = "search") {
            SearchMeetScreenRoot(
                modifier = modifier,
            )
        }
        composable(route = "category") {
            CategoryScreenRoot(
                modifier = modifier,
            )
        }
        composable(route = "banner") {
            BannerScreenRoot(
                modifier = modifier,
            )
        }
        composable(route = "announcement") {
            AnnouncementScreenRoot(
                modifier = modifier,
            )
        }
        composable(route = "recruitment") {
            RecruitmentScreenRoot(
                modifier = modifier,
            )
        }

    }
}

private fun NavGraphBuilder.settingGraph(
    navController: NavController,
    isLoggedIn: Boolean,
    modifier: Modifier = Modifier,
) {
    navigation(
        startDestination = "setting_overview",
        route = "setting"
    ) {
        composable("setting_overview") {
            SettingScreenRoot(
                modifier = modifier,
            )
        }
    }
}

private fun NavGraphBuilder.authGraph(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    navigation(
        startDestination = "login",
        route = "auth"
    ) {
        composable(route = "login") {
            LoginScreenRoot(
                modifier = modifier,
            )
        }
        composable(route = "register") {
            RegisterScreenRoot(
                modifier = modifier
            )
        }
    }
}
