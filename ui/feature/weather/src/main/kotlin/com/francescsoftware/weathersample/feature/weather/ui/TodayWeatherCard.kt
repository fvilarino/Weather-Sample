package com.francescsoftware.weathersample.feature.weather.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.francescsoftware.weathersample.shared.composable.weather.CurrentWeather
import com.francescsoftware.weathersample.shared.composable.weather.CurrentWeatherState
import com.francescsoftware.weathersample.styles.CardElevation
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.styles.WidgetPreviews

@Composable
internal fun TodayWeatherCard(
    state: CurrentWeatherState,
    modifier: Modifier = Modifier,
    elevation: Dp = CardElevation,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation,
        ),
    ) {
        CurrentWeather(
            state = state,
            modifier = Modifier.padding(all = MarginSingle)
        )
    }
}

@WidgetPreviews
@Composable
private fun TodayWeatherCardPreview() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            val state = CurrentWeatherState(
                temperature = "16.4°C",
                feelsLikeTemperature = "14.3°C",
                precipitation = "10",
                uvIndex = "3",
                description = "Partly cloudy",
                iconId = com.francescsoftware.weathersample.shared.assets.R.drawable.ic_partly_cloudy,
                windSpeed = "4.3kph",
                humidity = "54%",
                pressure = "1024mb",
                visibility = "10000 m",
            )
            TodayWeatherCard(state = state)
        }
    }
}
