package com.bestapp.zipbab.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


private val DarkColorScheme = darkColorScheme(
    primary = MainColor,
    secondary = MainColorLight,
    tertiary = MainColorTransparent20,
    background = LightGray,
)

private val LightColorScheme = lightColorScheme(
    primary = MainColor,
    secondary = MainColorLight,
    tertiary = MainColorTransparent20,
    background = LightGray,
    primaryContainer = Color.White,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

val OnLightCustomColorsPalette = CustomColorsPalette(
    mainColor = MainColor,
    unselected = Unselected,
    mainColorTransparent20 = MainColorTransparent20,
    mainColorTransparent50 = MainColorTransparent50,
    lightGray = LightGray,
    temperatureMin0 = TemperatureMin0Day,
    temperatureMin30 = TemperatureMin30,
    temperatureMin40 = TemperatureMin40,
    temperatureMin60 = TemperatureMin60,
    temperatureMin80 = TemperatureMin80,
    temperatureTrack = TemperatureTrack,
)

val OnDarkCustomColorsPalette = CustomColorsPalette(
    mainColor = MainColor,
    unselected = Unselected,
    mainColorTransparent20 = MainColorTransparent20,
    mainColorTransparent50 = MainColorTransparent50,
    lightGray = LightGray,
    temperatureMin0 = TemperatureMin0Night,
    temperatureMin30 = TemperatureMin30,
    temperatureMin40 = TemperatureMin40,
    temperatureMin60 = TemperatureMin60,
    temperatureMin80 = TemperatureMin80,
    temperatureTrack = TemperatureTrack,
)

@Composable
fun ZipbabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val customColorsPalette = if (darkTheme) OnDarkCustomColorsPalette else OnLightCustomColorsPalette

//    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            val window = if (view.context is ViewComponentManager.FragmentContextWrapper) {
//                (view.context as ViewComponentManager.FragmentContextWrapper).baseContext.
//            } else {
//                (view.context as Activity).window
//            }
//            window.statusBarColor = colorScheme.primary.toArgb()
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
//        }
//    }
    CompositionLocalProvider(LocalCustomColorsPalette provides customColorsPalette) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = zipbabTypography,
            content = content
        )
    }
}
