package com.bestapp.zipbab.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bestapp.zipbab.R
import com.bestapp.zipbab.domain.model.category.CategoryGroup
import com.bestapp.zipbab.domain.model.category.CategoryIcon
import com.bestapp.zipbab.domain.model.category.CategoryState
import com.bestapp.zipbab.theme.LocalCustomColorsPalette
import com.bestapp.zipbab.theme.ZipbabTheme
import kotlinx.coroutines.delay


private const val MAX_CATEGORY_ICON_SIZE = 8

@Composable
fun HomeScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
) {
    HomeScreen(
        homeState = viewModel.state,
        onAction = viewModel::onAction,
        modifier = modifier,
    )
}

@Composable
fun HomeScreen(
    homeState: HomeState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        item {
            TopSection(
                isAlertExist = homeState.isAlertExist,
                onAlertClick = { onAction(HomeAction.OnAlertClick) },
            )
        }

        item {
            SearchSection(
                onSearchClick = {
                    onAction(HomeAction.OnSearchClick)
                },
            )
        }


        item {
            AnimatedVisibility(
                visible = homeState.announcementText.isNotBlank(),
                enter = slideInVertically {
                    with(density) {
                        40.dp.roundToPx()
                    }
                } + expandVertically {
                    with(density) {
                        40.dp.roundToPx()
                    }
                } + fadeIn(
                    initialAlpha = 0.3f
                )
            ) {
                GradientBackground {
                    AnnouncementSection(
                        displayText = homeState.announcementText,
                        eventId = homeState.announcementId,
                        onAnnouncementNotificationClick = {
                            onAction(HomeAction.OnAnnouncementNotificationClick)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                    )
                }

            }
        }

        item {
            TabSection(
                categories = homeState.categories,
                onCategoryItemClick = { onAction(HomeAction.OnCategoryClick(it)) },
                onCategoryCreateClick = { onAction(HomeAction.OnCategoryCreateClick) }
            )
        }
    }
}

@Composable
fun TopSection(
    isAlertExist: Boolean,
    onAlertClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 18.dp),
        )
        BadgedBox(
            modifier = Modifier
                .padding(end = 18.dp)
                .clickable(onClick = onAlertClick),
            badge = {
                if (isAlertExist) {
                    Badge()
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "알림",
            )
        }
    }
}

@Composable
fun SearchSection(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit,
) {
    Button(
        onClick = onSearchClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(width = 1.dp, color = LocalCustomColorsPalette.current.mainColor),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 12.dp)
    ) {
        Row {
            Text(
                text = stringResource(R.string.search_meet_home_screen),
                fontWeight = FontWeight.Thin,
                color = LocalCustomColorsPalette.current.unselected,
                fontSize = 18.sp,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = LocalCustomColorsPalette.current.mainColor,
            )
        }
    }

}

@Composable
fun AnnouncementSection(
    displayText: String,
    eventId: String,
    onAnnouncementNotificationClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clickable(
                enabled = eventId.isNotBlank(),
                onClick = onAnnouncementNotificationClick,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val buildAnnotatedString = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            ) {
                append(displayText)

                if (eventId.isNotBlank()) {
                    append(" " + stringResource(id = R.string.go_symbol_text))
                }
            }
        }

        Text(
            text = buildAnnotatedString
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TabSection(
    categories: List<CategoryGroup>,
    onCategoryItemClick: (CategoryGroup) -> Unit,
    onCategoryCreateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val tabWidths = remember(categories.size) {
        Array(categories.size) {
            0.dp
        }
    }

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState {
        categories.size
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
            categories.forEachIndexed { index, item ->
                Tab(
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(
                                topStart = if (index == 0) 16.dp else 0.dp,
                                topEnd = if (index == categories.lastIndex) 16.dp else 0.dp,
                            )
                        )
                        .background(Color.White),
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
            val category = categories[index]

            Column(
                modifier = Modifier.background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.White),
                    contentAlignment = Alignment.Center,
                ) {
                    FlowRow(
                        maxItemsInEachRow = 4,
                        modifier = Modifier.padding(horizontal = 32.dp, vertical = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(36.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                    ) {
                        if (category.state == CategoryState.READY) {
                            for (i in 0 until MAX_CATEGORY_ICON_SIZE) {
                                PlaceHolderCategoryItem()
                            }
                        } else {
                            val displayedIcons = category.icons.take(MAX_CATEGORY_ICON_SIZE)
                            val placeHoldersCount = MAX_CATEGORY_ICON_SIZE - displayedIcons.size
                            val items = displayedIcons + List(placeHoldersCount) {
                                CategoryIcon("", "PlaceHolder $it")
                            }
                            for (icon in items) {
                                if (icon.label.contains("PlaceHolder")) {
                                    PlaceHolderCategoryItem()
                                } else {
                                    CategoryItem(
                                        imageUrl = icon.imageUrl,
                                        name = icon.label,
                                        onClick = {
                                            onCategoryItemClick(category)
                                        }
                                    )
                                }
                            }
                        }
                    }
                    if (category.state == CategoryState.READY) {
                        Text(
                            text = stringResource(R.string.category_is_not_available),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }

                }
                HorizontalDivider(
                    thickness = 2.dp,
                    color = LocalCustomColorsPalette.current.lightGray
                )
                CreateMeetText(
                    name = category.name,
                    isClickable = category.state == CategoryState.AVAILABLE,
                    onClick = onCategoryCreateClick,
                )
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
        Text(" ")
    }
}

@Composable
fun CreateMeetText(
    name: String,
    isClickable: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = isClickable,
                onClick = onClick,
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(name)
                }
                append(" ")
                append(stringResource(R.string.create_meet_home_screen))
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(stringResource(id = R.string.go_symbol_text))
                }
            },
            modifier = Modifier
                .padding(vertical = 12.dp),
            color = Color.Unspecified.copy(alpha = if (isClickable) 1f else 0.3f)
        )
    }
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
        var state by remember {
            mutableStateOf(
                HomeState(
                    isLoading = false,
                    isAlertExist = true,
                    announcementText = "",
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
        HomeScreen(
            homeState = state,
            onAction = {},
        )
        LaunchedEffect(Unit) {
            delay(2000)
            state = state.copy(
                announcementText = "집밥이 바꼈습니다!"
            )
        }
    }
}
