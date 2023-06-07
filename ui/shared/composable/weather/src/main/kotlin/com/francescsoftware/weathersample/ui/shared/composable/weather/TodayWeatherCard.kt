package com.francescsoftware.weathersample.ui.shared.composable.weather

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.francescsoftware.weathersample.core.type.weather.AverageVisibility
import com.francescsoftware.weathersample.core.type.weather.Humidity
import com.francescsoftware.weathersample.core.type.weather.Precipitation
import com.francescsoftware.weathersample.core.type.weather.Pressure
import com.francescsoftware.weathersample.core.type.weather.Speed
import com.francescsoftware.weathersample.core.type.weather.Temperature
import com.francescsoftware.weathersample.core.type.weather.UvIndex
import com.francescsoftware.weathersample.ui.shared.assets.R
import com.francescsoftware.weathersample.ui.shared.styles.CardElevation
import com.francescsoftware.weathersample.ui.shared.styles.MarginSingle
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews

/**
 * Display a [Card] with the current weather
 *
 * @param state the [CurrentWeather] containing the weather information
 * @param modifier the [Modifier] to apply to this weather card
 * @param elevation the elevation for the weather card
 */
@Composable
fun TodayWeatherCard(
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
                temperature = Temperature.fromCelsius(16.4),
                feelsLikeTemperature = Temperature.fromCelsius(14.3),
                precipitation = Precipitation.fromMillimeters(12.4),
                uvIndex = UvIndex(2),
                description = "Partly cloudy",
                iconId = R.drawable.ic_partly_cloudy,
                windSpeed = Speed.fromKph(4.3),
                gustSpeed = Speed.fromKph(7.5),
                humidity = Humidity(54),
                pressure = Pressure.fromMillibars(1024.0),
                visibility = AverageVisibility.fromKm(3.5),
            )
            TodayWeatherCard(state = state)
        }
    }
}
