package com.bestapp.zipbab.home

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bestapp.zipbab.theme.ZipbabTheme

@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenWidthPx = with(density) {
        configuration.screenWidthDp.dp.roundToPx()
    }
    // 화면의 가로, 세로 중 더 작은 길이를 선택 -> 화면 회전 고
    val smallDimension = minOf(
        configuration.screenWidthDp.dp,
        configuration.screenHeightDp.dp,
    )
    val smallDimensionPx = with(density) {
        smallDimension.roundToPx()
    }

    val primaryColor = MaterialTheme.colorScheme.primary
    val isAtLeastAndroid12 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    Box(
        modifier = modifier
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .then(
                    if (isAtLeastAndroid12) {
                        Modifier.blur(50.dp)
                    } else Modifier
                )
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            if (isAtLeastAndroid12) primaryColor else primaryColor.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.background
                        ),
                        center = Offset(
                            x = screenWidthPx / 2f,
                            y = smallDimensionPx / 3f
                        ),
                        radius = smallDimensionPx / 3f,
                    )
                )
        )
        content()
    }
}


@Preview(
    apiLevel = 34
)
@Composable
private fun GradientBackgroundPreview() {
    ZipbabTheme {
        GradientBackground(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "배민B마트",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "지금 장보면 아침 7시부터 배달!",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
//            Text(
//                text = "Hello World!",
//            )
        }
    }
}
