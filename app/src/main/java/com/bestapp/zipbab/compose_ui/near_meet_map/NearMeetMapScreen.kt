package com.bestapp.zipbab.compose_ui.near_meet_map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NearMeetMapScreenRoot(
    modifier: Modifier = Modifier,
) {
    NearMeetMapScreen(
        modifier = modifier,
    )
}

@Composable
fun NearMeetMapScreen(
    modifier: Modifier = Modifier
) {
    Scaffold {  paddingValue ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValue)
        )
    }
}
