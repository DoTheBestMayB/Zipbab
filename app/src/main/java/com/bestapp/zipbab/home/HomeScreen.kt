package com.bestapp.zipbab.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bestapp.zipbab.R
import com.bestapp.zipbab.domain.model.category.CategoryGroup
import com.bestapp.zipbab.domain.model.category.CategoryIcon

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
) {
    val alertUiState by viewModel.alertUiState.collectAsStateWithLifecycle()
    val flashMeetCategoryUiState by viewModel.flashMeetCategoryUiState.collectAsStateWithLifecycle()

    HomeScreen(
        alertUiState = alertUiState,
        flashMeetCategoryUiState = flashMeetCategoryUiState,
        modifier = modifier,
    )
}

@Composable
fun HomeScreen(
    alertUiState: AlertUiState,
    flashMeetCategoryUiState: CategoryUiState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopBar(isAlertExist = alertUiState is AlertUiState.Exist)
        },
        bottomBar = { BottomNavigationBar() },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            AnnouncementSection()
            TabSection(categoryUiState = flashMeetCategoryUiState)
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
) {
    if (categoryUiState is CategoryUiState.Success) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.padding(32.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            items(
                items = categoryUiState.categories.icons,
                key = { it.label }
            ) { icon ->
                CategoryItem(
                    imageUrl = icon.imageUrl,
                    name = icon.label,
                )
            }
        }
    }
}

@Composable
fun CategoryItem(
    imageUrl: String,
    name: String,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.width(48.dp)
                .height(48.dp)
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = name)
    }
}

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier) {

}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        alertUiState = AlertUiState.Exist,
        flashMeetCategoryUiState = CategoryUiState.Success(
            categories = CategoryGroup(
                icons = listOf(
                    CategoryIcon("", "A"),
                    CategoryIcon("", "B"),
                    CategoryIcon("", "C"),
                    CategoryIcon("", "D"),
                    CategoryIcon("", "E"),
                    CategoryIcon("", "F"),
                )
            )
        )
    )
}

@Preview
@Composable
private fun TopBarPreview() {
    TopBar(isAlertExist = false)
}

@Preview
@Composable
private fun TabSectionPreview() {
    TabSection(categoryUiState = CategoryUiState.Success(
        categories = CategoryGroup(
            icons = listOf(
                CategoryIcon("", "A"),
                CategoryIcon("", "B"),
                CategoryIcon("", "C"),
                CategoryIcon("", "D"),
                CategoryIcon("", "E"),
                CategoryIcon("", "F"),
            )
        )
    ))
}
