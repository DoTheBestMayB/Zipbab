package com.bestapp.zipbab.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bestapp.zipbab.theme.ZipbabTheme

/**
 * @param endPercent 더 짧은 변으로부터 몇 %에서 원이 끝나도록 할 것인지 설정하는 변수
 * @param startColor 원의 중심점 색상
 */
@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    endPercent: Float = 0.05f,
    startColor: Color = MaterialTheme.colorScheme.secondary,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .drawWithCache {
                    onDrawBehind {
                        val endColor = Color.Transparent

                        // 뷰포트의 최대 치수보다 크게 설정 (상단까지 부드럽게 퍼지도록)
                        val radius = maxOf(size.width, size.height) * 0.4f

                        // scale로 타원화(가로/세로 비율 조정)
                        val scaleX = 1.55f // 가로는 많이 차지하도록 설정
                        val scaleY = 0.7f

                        // 그라디언트 중심: Box 하단 중앙
                        val center = Offset(
                            x = size.width / 2f,
                            y = size.height * endPercent + radius * scaleY
                        )

                        // '원형' 그라디언트 브러시 (실제 사용 시엔 translate/scale로 타원화)
                        val radialBrush = Brush.radialGradient(
                            colors = listOf(startColor, endColor),
                            center = Offset.Zero, // 실제 draw 때 옮길 예정
                            radius = radius
                        )

                        // 그리기 시작
                        drawContext.canvas.save()

                        // 하단 가운데로 이동
                        drawContext.canvas.translate(center.x, center.y)


                        drawContext.canvas.scale(scaleX, scaleY)

                        // 대형 사각형(원 중심 기준) 영역에 그라디언트 그리기
                        drawRect(
                            brush = radialBrush,
                            topLeft = Offset(-radius, -radius),
                            size = Size(2 * radius, 2 * radius)
                        )

                        drawContext.canvas.restore()
                    }
                }
        )

        // 최종적으로 이 안에 들어갈 실제 content
        Box {
            content()
        }
    }
}


@Preview(
    apiLevel = 34
)
@Composable
private fun GradientBackgroundPreview() {
    ZipbabTheme {
        GradientBackground {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "번개 모임",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "오늘 바로 만날 수 있는 모임을 찾아보세요!",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
