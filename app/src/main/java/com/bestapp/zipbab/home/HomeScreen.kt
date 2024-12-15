package com.bestapp.zipbab.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bestapp.zipbab.R
import com.bestapp.zipbab.UserPrivateUiState
import com.bestapp.zipbab.domain.model.category.CategoryGroup
import com.bestapp.zipbab.domain.model.category.CategoryIcon
import com.bestapp.zipbab.domain.model.category.CategoryState
import com.bestapp.zipbab.theme.LocalCustomColorsPalette
import com.bestapp.zipbab.theme.ZipbabTheme


private const val MAX_CATEGORY_ICON_SIZE = 8

@Composable
fun HomeScreen(
    userPrivateUiState: UserPrivateUiState,
    categoryUiState: CategoryUiState,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
) {

    HomeScreen(
        userUiState = userPrivateUiState,
        categoryUiState = categoryUiState,
        modifier = modifier,
    )
}

@Composable
fun HomeScreen(
    userUiState: UserPrivateUiState,
    categoryUiState: CategoryUiState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopBar(isAlertExist = userUiState is UserPrivateUiState.LoggedIn && userUiState.userPrivate.notifications.isNotEmpty())
        },
        bottomBar = { BottomNavigationBar() },
        modifier = modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            AnnouncementSection()
            TabSection(categoryUiState = categoryUiState)
        }

    }
}

@Composable
fun TopBar(
    isAlertExist: Boolean,
    modifier: Modifier = Modifier,
    onAlertClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.app_name),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 18.dp),
        )
        Box(
            modifier = Modifier
                .padding(end = 18.dp)
                .clickable(onClick = onAlertClick)
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "알림",
            )
            if (isAlertExist) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .align(Alignment.TopEnd)
                        .background(Color.Red, shape = CircleShape)
                )
            }
        }
    }
}

@Composable
fun AnnouncementSection(modifier: Modifier = Modifier) {

}

@Composable
fun TabSection(
    categoryUiState: CategoryUiState,
    modifier: Modifier = Modifier,
    onCategoryItemClick: (CategoryGroup, CategoryIcon) -> Unit = { _, _ -> },
    onCategoryCreateClick: (CategoryGroup) -> Unit = {},
) {
    if (categoryUiState !is CategoryUiState.Success) {
        return
    }
    val density = LocalDensity.current
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(categoryUiState.categories.size) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState {
        categoryUiState.categories.size
    }
    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = LocalCustomColorsPalette.current.lightGray,
            indicator = { tabPositions ->
                if (tabPositions.isNotEmpty() && tabWidths.size > selectedTabIndex) {
                    val currentTabWidth = tabWidths[selectedTabIndex]
                    val currentTabPosition = tabPositions[selectedTabIndex]

                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.customTabIndicatorOffset(
                            currentTabPosition = currentTabPosition,
                            tabWidth = currentTabWidth,
                        ),
                        color = Color.Black,
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            categoryUiState.categories.forEachIndexed { index, item ->
                Tab(
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(
                                topStart = if (index == 0) 16.dp else 0.dp,
                                topEnd = if (index == categoryUiState.categories.lastIndex) 16.dp else 0.dp,
                            )
                        ).background(Color.White),
                    selected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                    },
                    text = {
                        Text(
                            text = item.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (index == selectedTabIndex) {
                                Color.Black
                            } else {
                                LocalCustomColorsPalette.current.unselected
                            },
                            onTextLayout = { textLayoutResult ->
                                tabWidths[index] = with(density) {
                                    textLayoutResult.size.width.toDp()
                                }
                            }
                        )
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { index ->
            val category = categoryUiState.categories[index]

            when (category.state) {
                CategoryState.READY -> {
                    // 두 줄 높이를 유지하기 위한 invisible placeholder
                    Column(
                        modifier = Modifier.background(Color.White)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(Color.White),
                            contentAlignment = Alignment.Center,
                        ) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(4),
                                modifier = Modifier.padding(horizontal = 32.dp, vertical = 24.dp),
                                horizontalArrangement = Arrangement.spacedBy(24.dp),
                                verticalArrangement = Arrangement.spacedBy(24.dp),
                                userScrollEnabled = false,
                            ) {
                                items(MAX_CATEGORY_ICON_SIZE) {
                                    PlaceHolderCategoryItem()
                                }
                            }
                            Text(
                                text = stringResource(R.string.category_is_not_available),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                        HorizontalDivider(
                            thickness = 2.dp,
                            color = LocalCustomColorsPalette.current.lightGray
                        )
                        CreateMeetText(name = category.name)
                    }
                }

                CategoryState.AVAILABLE -> {
                    val displayedIcons = category.icons.take(MAX_CATEGORY_ICON_SIZE)
                    val placeHoldersCount = MAX_CATEGORY_ICON_SIZE - displayedIcons.size
                    val items = displayedIcons + List(placeHoldersCount) {
                        CategoryIcon("", "PlaceHolder $it")
                    }

                    Column(
                        modifier = Modifier.background(Color.White),
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(4),
                            modifier = Modifier.padding(horizontal = 32.dp, vertical = 24.dp),
                            horizontalArrangement = Arrangement.spacedBy(24.dp),
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                            userScrollEnabled = false,
                        ) {
                            items(items = items, key = { it.label }) { icon ->
                                if (icon.label.contains("PlaceHolder")) {
                                    PlaceHolderCategoryItem()
                                } else {
                                    CategoryItem(
                                        imageUrl = icon.imageUrl,
                                        name = icon.label,
                                        onClick = {
                                            onCategoryItemClick(category, icon)
                                        }
                                    )
                                }
                            }
                        }
                        HorizontalDivider(
                            thickness = 2.dp,
                            color = LocalCustomColorsPalette.current.lightGray
                        )
                        CreateMeetText(
                            name = category.name,
                            onClick = {
                                onCategoryCreateClick(category)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    imageUrl: String,
    name: String,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = name)
    }
}

@Composable
fun PlaceHolderCategoryItem(modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.size(48.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        // Empty text placeholder space (could be invisible text or just a Spacer)
        Text(" ")
    }
}

@Composable
fun CreateMeetText(
    name: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(name)
                }
                append(" 생성하러 가기")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(" >")
                }
            },
            modifier = Modifier
                .padding(vertical = 12.dp)
                .clickable { onClick() },
        )
    }
}

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier) {

}

fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition,
    tabWidth: Dp
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "customTabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = tabWidth,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    val indicatorOffset by animateDpAsState(
        targetValue = ((currentTabPosition.left + currentTabPosition.right - tabWidth) / 2),
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}

@Preview
@Composable
private fun HomeScreenPreview() {
    ZipbabTheme {
        HomeScreen(
            userUiState = UserPrivateUiState.NotLoggedIn,
            categoryUiState = CategoryUiState.Success(
                categories = listOf(
                    CategoryGroup(
                        name = "flashMeet",
                        state = CategoryState.AVAILABLE,
                        icons = listOf(
                            CategoryIcon("", "A"),
                            CategoryIcon("", "B"),
                            CategoryIcon("", "C"),
                            CategoryIcon("", "D"),
                            CategoryIcon("", "E"),
                            CategoryIcon("", "F"),
                        )
                    ),
                    CategoryGroup(
                        name = "meet",
                        state = CategoryState.READY,
                        icons = listOf(
                            CategoryIcon("", "A"),
                            CategoryIcon("", "B"),
                            CategoryIcon("", "C"),
                            CategoryIcon("", "D"),
                            CategoryIcon("", "E"),
                            CategoryIcon("", "F"),
                        )
                    ),
                    CategoryGroup(
                        name = "test",
                        state = CategoryState.AVAILABLE,
                        icons = listOf(
                            CategoryIcon("", "A"),
                            CategoryIcon("", "B"),
                            CategoryIcon("", "C"),
                        )
                    )
                )
            )
        )
    }
}

@Preview
@Composable
private fun TopBarPreview() {
    TopBar(isAlertExist = false)
}

@Preview()
@Composable
private fun TabSectionPreview() {
    ZipbabTheme {
        TabSection(
            categoryUiState = CategoryUiState.Success(
                categories = listOf(
                    CategoryGroup(
                        name = "flashMeet",
                        state = CategoryState.AVAILABLE,
                        icons = listOf(
                            CategoryIcon("", "A"),
                            CategoryIcon("", "B"),
                            CategoryIcon("", "C"),
                            CategoryIcon("", "D"),
                            CategoryIcon("", "E"),
                            CategoryIcon("", "F"),
                        )
                    ),
                    CategoryGroup(
                        name = "meet",
                        state = CategoryState.READY,
                        icons = listOf(
                            CategoryIcon("", "A"),
                            CategoryIcon("", "B"),
                            CategoryIcon("", "C"),
                            CategoryIcon("", "D"),
                            CategoryIcon("", "E"),
                            CategoryIcon("", "F"),
                        )
                    ),
                    CategoryGroup(
                        name = "test",
                        state = CategoryState.AVAILABLE,
                        icons = listOf(
                            CategoryIcon("", "A"),
                            CategoryIcon("", "B"),
                            CategoryIcon("", "C"),
                        )
                    )
                )
            )
        )
    }
}
