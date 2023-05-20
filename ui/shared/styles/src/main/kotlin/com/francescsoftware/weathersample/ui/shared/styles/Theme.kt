package com.francescsoftware.weathersample.ui.shared.styles

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

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
    val colorScheme = if (supportsDynamicTheming()) {
        val context = LocalContext.current
        if (darkTheme) {
            dynamicDarkColorScheme(context)
        } else {
            dynamicLightColorScheme(context)
        }
    } else {
        if (darkTheme) DarkColorScheme else LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
private fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
