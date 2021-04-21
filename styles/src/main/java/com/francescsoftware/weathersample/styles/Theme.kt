package com.francescsoftware.weathersample.styles

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = DeepPurple500,
    primaryVariant = DeepPurple700,
    secondary = Teal700,
    onPrimary = Color.White,
    onSecondary = Color.White,
    surface = Grey900,
    background = Grey950,
)

private val LightColorPalette = lightColors(
    primary = Purple700,
    primaryVariant = Purple900,
    secondary = Teal700,
    onPrimary = Color.White,
    onSecondary = Color.White,
    surface = Color.White,
    background = Grey050,
)

@Composable
fun WeatherSampleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
