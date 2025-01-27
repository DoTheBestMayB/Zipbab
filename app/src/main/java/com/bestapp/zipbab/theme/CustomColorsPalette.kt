package com.bestapp.zipbab.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CustomColorsPalette(
    val defaultBackgroundColor: Color = Color.Unspecified,
    val defaultForegroundColor: Color = Color.Unspecified,
    val mainColor: Color = Color.Unspecified,
    val mainColorTransparent20: Color = Color.Unspecified,
    val mainColorTransparent50: Color = Color.Unspecified,
    val lightGray: Color = Color.Unspecified,
    val unselected: Color = Color.Unspecified,
    val temperatureMin0: Color = Color.Unspecified,
    val temperatureMin30: Color = Color.Unspecified,
    val temperatureMin40: Color = Color.Unspecified,
    val temperatureMin60: Color = Color.Unspecified,
    val temperatureMin80: Color = Color.Unspecified,
    val temperatureTrack: Color = Color.Unspecified,
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }
