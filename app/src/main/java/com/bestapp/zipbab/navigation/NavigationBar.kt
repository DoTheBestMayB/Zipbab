package com.bestapp.zipbab.navigation

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bestapp.zipbab.R


data class TopLevelRoute<T : Any>(
    val name: String,
    val route: T,
    val icon: ImageVector,
)

// ref : https://developer.android.com/develop/ui/compose/navigation#bottom-nav
@Composable
fun ZipbabNavigationBar(
    navController: NavController,
) {
    val topLevelRoutes = listOf(
        TopLevelRoute(stringResource(R.string.home), Main, Icons.Default.Home),
        TopLevelRoute(stringResource(R.string.nearMeetMap), NearMeetMap, ImageVector.vectorResource(id = R.drawable.baseline_map_24)),
        TopLevelRoute(stringResource(R.string.setting), Setting, Icons.Default.Person),
    )

    BottomNavigation(
        backgroundColor = Color.White,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        topLevelRoutes.forEach { topLevelRoute ->
            val selected = currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class)} == true
            BottomNavigationItem(
                // bottom inset을 여기서 적용해야 하단 바와 떨어지면서 배경은 이어지도록 설정할 수 있음
                modifier = Modifier.navigationBarsPadding(),
                icon = {
                    Icon(
                        imageVector = topLevelRoute.icon,
                        contentDescription = topLevelRoute.name,
                        tint = if (selected) LocalContentColor.current else Color.LightGray,
                    )
                },
                label = {
                    Text(
                        text = topLevelRoute.name,
                        color = if (selected) Color.Black else Color.LightGray
                    )
                },
                selected = selected,
                onClick = {
                    navController.navigate(topLevelRoute.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                selectedContentColor = MaterialTheme.colorScheme.tertiary,
            )
        }
    }
}
