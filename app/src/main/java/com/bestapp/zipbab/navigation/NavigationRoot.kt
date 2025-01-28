package com.bestapp.zipbab.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
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
import com.bestapp.zipbab.domain.model.user.MeetType

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean,
    modifier: Modifier = Modifier,
) {
    NavHost(
        startDestination = Main,
        navController = navController,
    ) {
        mainGraph(navController, isLoggedIn, modifier)
        settingGraph(navController, modifier)
        authGraph(navController, modifier)
    }
}

private fun NavGraphBuilder.mainGraph(
    navController: NavController,
    isLoggedIn: Boolean,
    modifier: Modifier = Modifier,
) {
    navigation<Main>(
        startDestination = Home,
    ) {
        composable<Home> {
            HomeScreenRoot(
                onSearchClick = {
                    navController.navigate(Search)
                },
                onAlertClick = {
                    if (isLoggedIn) {
                        navController.navigate(Alert)
                    } else {
                        navController.navigate(Auth)
                    }
                },
                onAnnouncementNotificationClick = {
                    navController.navigate(Announcement(it))
                },
                onCategoryItemClick = {
                    navController.navigate(Category(it, MeetType.FLASH_MEET))
                },
                onCategoryCreateClick = {
                    navController.navigate(Recruitment)
                },
                onBannerClick = {
                    navController.navigate(Banner(it))
                },
                modifier = modifier,
            )
        }
        composable<Alert> {
            AlertScreenRoot(
                modifier = modifier,
            )
        }
        composable<Search> { backstackEntry ->
            SearchMeetScreenRoot(
                modifier = modifier,
            )
        }
        composable<Category> {
            val category = it.toRoute<Category>()
            CategoryScreenRoot(
                meetType = category.meetType,
                label = category.label,
                modifier = modifier,
            )
        }
        composable<Banner> {
            val banner = it.toRoute<Banner>()
            BannerScreenRoot(
                contentUrl = banner.contentUrl,
                modifier = modifier,
            )
        }
        composable<Announcement> {
            val announcement = it.toRoute<Announcement>()
            AnnouncementScreenRoot(
                eventId = announcement.eventId,
                modifier = modifier,
            )
        }
        composable<Recruitment> {
            RecruitmentScreenRoot(
                modifier = modifier,
            )
        }

    }
}

private fun NavGraphBuilder.settingGraph(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    navigation<Setting>(
        startDestination = SettingOverview,
    ) {
        composable<SettingOverview> {
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
    navigation<Auth>(
        startDestination = Login,
    ) {
        composable<Login> {
            LoginScreenRoot(
                modifier = modifier,
            )
        }
        composable<Register> {
            RegisterScreenRoot(
                modifier = modifier
            )
        }
    }
}
