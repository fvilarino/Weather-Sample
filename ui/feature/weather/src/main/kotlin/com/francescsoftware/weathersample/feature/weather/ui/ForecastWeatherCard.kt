package com.francescsoftware.weathersample.feature.weather.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import com.francescsoftware.weathersample.shared.composable.weather.ForecastHourState
import com.francescsoftware.weathersample.shared.composable.weather.ForecastWeather
import com.francescsoftware.weathersample.styles.CardElevation
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.styles.WidgetPreviews

@Composable
internal fun ForecastWeatherCard(
    state: ForecastHourState,
    modifier: Modifier = Modifier,
    elevation: Dp = CardElevation,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation,
        ),
    ) {
        ForecastWeather(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MarginSingle)
        )
    }
}

@WidgetPreviews
@Composable
private fun ForecastWeatherCardPreview(
    @PreviewParameter(ForecastStateProvider::class) forecastState: ForecastHourState,
) {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            ForecastWeatherCard(
                state = forecastState,
                modifier = Modifier.padding(all = MarginSingle),
            )
        }
    }
}
