package com.francescsoftware.weathersample.feature.weather

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal val PartlyCloudyForecast = ForecastItem.ForecastCard(
    id = 1L,
    header = "02:00 - Scattered Clouds",
    iconId = R.drawable.ic_partly_cloudy,
    minTemperature = "16.4°C",
    maxTemperature = "23.7°C",
    feelsLikeTemperature = "15.5°C",
    windSpeed = "5.4 kph",
    humidity = "48 %",
    visibility = "3000 m",
)

internal val SunnyForecast = ForecastItem.ForecastCard(
    id = 2L,
    header = "14:00 - Sunny",
    iconId = R.drawable.ic_sunny,
    minTemperature = "18.7°C",
    maxTemperature = "28.2°C",
    feelsLikeTemperature = "21.5°C",
    windSpeed = "14.1 kph",
    humidity = "75 %",
    visibility = "10000 m",
)

internal val HeavyRainForecast = ForecastItem.ForecastCard(
    id = 1L,
    header = "06:00 - Heavy Rain",
    iconId = R.drawable.ic_heavy_rain,
    minTemperature = "6.2°C",
    maxTemperature = "13.5°C",
    feelsLikeTemperature = "5.0°C",
    windSpeed = "8.2 kph",
    humidity = "100 %",
    visibility = "1000 m",
)

internal class ForecastStateProvider : PreviewParameterProvider<ForecastItem.ForecastCard> {
    override val values: Sequence<ForecastItem.ForecastCard> = sequenceOf(
        PartlyCloudyForecast,
        SunnyForecast,
        HeavyRainForecast,
    )
}
