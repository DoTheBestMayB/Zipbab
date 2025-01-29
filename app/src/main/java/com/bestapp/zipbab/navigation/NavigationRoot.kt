package com.bestapp.zipbab.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import com.bestapp.zipbab.compose_ui.near_meet_map.NearMeetMapScreenRoot
import com.bestapp.zipbab.compose_ui.recruitment.RecruitmentScreenRoot
import com.bestapp.zipbab.compose_ui.register.RegisterScreenRoot
import com.bestapp.zipbab.compose_ui.search_meet.SearchMeetScreenRoot
import com.bestapp.zipbab.compose_ui.setting.SettingScreenRoot
import com.bestapp.zipbab.domain.model.user.MeetType

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean,
) {
    Scaffold(
        bottomBar = {
            ZipbabNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            startDestination = Main,
            navController = navController,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            },
            modifier = Modifier
                .padding(innerPadding)
                // bottom navigation bar를 위해 여기서 scaffold를 사용하고, 각 화면에서 topBar를 위해 scaffold를 또 사용한다.
                // innerPadding이 중복 적용되는 것을 방지하기 위해 consumeWindowInsets 함수를 호출함.
                .consumeWindowInsets(innerPadding)
        ) {
            mainGraph(
                navController = navController,
                isLoggedIn = isLoggedIn,
            )
            nearMapGraph(
                navController = navController,
            )
            settingGraph(
                navController = navController,
            )
            authGraph(navController)
        }
    }
}

private fun NavGraphBuilder.mainGraph(
    navController: NavController,
    isLoggedIn: Boolean,
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
            )
        }
        composable<Alert> {
            AlertScreenRoot()
        }
        composable<Search> { backstackEntry ->
            SearchMeetScreenRoot()
        }
        composable<Category> {
            val category = it.toRoute<Category>()
            CategoryScreenRoot(
                meetType = category.meetType,
                label = category.label,
            )
        }
        composable<Banner> {
            val banner = it.toRoute<Banner>()
            BannerScreenRoot(
                contentUrl = banner.contentUrl,
            )
        }
        composable<Announcement> {
            val announcement = it.toRoute<Announcement>()
            AnnouncementScreenRoot(
                eventId = announcement.eventId,
            )
        }
        composable<Recruitment> {
            RecruitmentScreenRoot()
        }

    }
}

private fun NavGraphBuilder.nearMapGraph(
    navController: NavController,
) {
    navigation<NearMeetMap>(
        startDestination = Map,
    ) {
        composable<Map> {
            NearMeetMapScreenRoot()
        }
    }
}

private fun NavGraphBuilder.settingGraph(
    navController: NavController,
) {
    navigation<Setting>(
        startDestination = SettingOverview,
    ) {
        composable<SettingOverview> {
            SettingScreenRoot()
        }
    }
}

private fun NavGraphBuilder.authGraph(
    navController: NavController,
) {
    navigation<Auth>(
        startDestination = Login,
    ) {
        composable<Login> {
            LoginScreenRoot()
        }
        composable<Register> {
            RegisterScreenRoot()
        }
    }
}
