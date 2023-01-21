package com.francescsoftware.weathersample.feature.weather.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.francescsoftware.weathersample.feature.weather.viewmodel.ForecastHourState
import com.francescsoftware.weathersample.shared.assets.R

internal val PartlyCloudyHourForecast = ForecastHourState(
    id = 1L,
    header = "02:00 - Scattered Clouds",
    iconId = R.drawable.ic_partly_cloudy,
    temperature = "16.4°C",
    feelsLikeTemperature = "15.5°C",
    precipitation = "0",
    uvIndex = "3",
    windSpeed = "5.4 kph",
    humidity = "48 %",
    visibility = "3 km",
)

internal val SunnyHourForecast = ForecastHourState(
    id = 2L,
    header = "14:00 - Sunny",
    iconId = R.drawable.ic_sunny,
    temperature = "18.7°C",
    feelsLikeTemperature = "21.5°C",
    precipitation = "10",
    uvIndex = "8",
    windSpeed = "14.1 kph",
    humidity = "75 %",
    visibility = "10 km",
)

internal val HeavyRainHourForecast = ForecastHourState(
    id = 3L,
    header = "06:00 - Heavy Rain",
    iconId = R.drawable.ic_heavy_rain,
    temperature = "6.2°C",
    feelsLikeTemperature = "5.0°C",
    precipitation = "120",
    uvIndex = "1",
    windSpeed = "8.2 kph",
    humidity = "100 %",
    visibility = "1 km",
)

internal val SnowHourForecast = ForecastHourState(
    id = 4L,
    header = "07:00 - Snow",
    iconId = R.drawable.ic_snow,
    temperature = "-3.4°C",
    feelsLikeTemperature = "-5.0°C",
    precipitation = "15",
    uvIndex = "1",
    windSpeed = "7.1 kph",
    humidity = "100 %",
    visibility = "0.5 km",
)

internal val LightRainHourForecast = ForecastHourState(
    id = 5L,
    header = "08:00 - Light Rain",
    iconId = R.drawable.ic_light_rain,
    temperature = "13.7°C",
    feelsLikeTemperature = "14.0°C",
    precipitation = "4",
    uvIndex = "3",
    windSpeed = "3.2 kph",
    humidity = "100 %",
    visibility = "4.5 km",
)

internal class ForecastStateProvider : PreviewParameterProvider<ForecastHourState> {
    override val values: Sequence<ForecastHourState> = sequenceOf(
        PartlyCloudyHourForecast,
        SunnyHourForecast,
        HeavyRainHourForecast,
        SnowHourForecast,
        LightRainHourForecast,
    )
}
