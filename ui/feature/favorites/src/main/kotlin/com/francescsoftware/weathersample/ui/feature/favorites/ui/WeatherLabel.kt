package com.francescsoftware.weathersample.ui.feature.favorites.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.styles.WidgetPreviews

@Composable
internal fun WeatherLabel(
    label: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = label,
        modifier = modifier,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall,
    )
}

@WidgetPreviews
@Composable
private fun PreviewWeatherLabel() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            WeatherLabel(
                label = "Current Weather",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
