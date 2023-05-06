package com.francescsoftware.weathersample.styles

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = DeepPurple500,
    secondary = DeepPurple700,
    tertiary = Teal700,
    onPrimary = Color.White,
    onSecondary = Color.White,
    surface = Grey900,
    background = Grey950,
)

private val LightColorScheme = lightColorScheme(
    primary = Purple700,
    secondary = Purple900,
    tertiary = Teal700,
    onPrimary = Color.White,
    onSecondary = Color.White,
    surface = Color.White,
    background = Grey050,
)

/**
 * App theme
 *
 * @param darkTheme - whether to use a dark theme
 * @param content - the app content
 */
@Composable
fun WeatherSampleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}
