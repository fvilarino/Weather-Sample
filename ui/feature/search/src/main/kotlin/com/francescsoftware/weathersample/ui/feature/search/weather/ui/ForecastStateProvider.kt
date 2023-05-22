@file: Suppress("MagicNumber")

package com.francescsoftware.weathersample.ui.feature.search.weather.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.francescsoftware.weathersample.core.type.weather.AverageVisibility
import com.francescsoftware.weathersample.core.type.weather.Humidity
import com.francescsoftware.weathersample.core.type.weather.Precipitation
import com.francescsoftware.weathersample.core.type.weather.Speed
import com.francescsoftware.weathersample.core.type.weather.Temperature
import com.francescsoftware.weathersample.core.type.weather.UvIndex
import com.francescsoftware.weathersample.ui.shared.assets.R
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHourState

internal val PartlyCloudyHourForecast = ForecastHourState(
    id = 1L,
    time = "02:00",
    description = "Scattered Clouds",
    iconId = R.drawable.ic_partly_cloudy,
    temperature = Temperature.fromCelsius(16.4),
    feelsLikeTemperature = Temperature.fromCelsius(15.5),
    precipitation = Precipitation.fromMillimeters(0.0),
    uvIndex = UvIndex(3),
    windSpeed = Speed.fromKph(5.4),
    humidity = Humidity(48),
    visibility = AverageVisibility.fromKm(3.0),
)

internal val SunnyHourForecast = ForecastHourState(
    id = 2L,
    time = "14:00",
    description = "Sunny",
    iconId = R.drawable.ic_sunny,
    temperature = Temperature.fromCelsius(18.7),
    feelsLikeTemperature = Temperature.fromCelsius(17.5),
    precipitation = Precipitation.fromMillimeters(9.5),
    uvIndex = UvIndex(0),
    windSpeed = Speed.fromKph(14.1),
    humidity = Humidity(75),
    visibility = AverageVisibility.fromKm(2.5),
)

internal val HeavyRainHourForecast = ForecastHourState(
    id = 3L,
    time = "06:00",
    description = "Heavy Rain",
    iconId = R.drawable.ic_heavy_rain,
    temperature = Temperature.fromCelsius(6.2),
    feelsLikeTemperature = Temperature.fromCelsius(5.0),
    precipitation = Precipitation.fromMillimeters(120.0),
    uvIndex = UvIndex(1),
    windSpeed = Speed.fromKph(0.2),
    humidity = Humidity(100),
    visibility = AverageVisibility.fromKm(1.0),
)

internal val SnowHourForecast = ForecastHourState(
    id = 4L,
    time = "07:00",
    description = "Snow",
    iconId = R.drawable.ic_snow,
    temperature = Temperature.fromCelsius(3.4),
    feelsLikeTemperature = Temperature.fromCelsius(-5.5),
    precipitation = Precipitation.fromMillimeters(15.0),
    uvIndex = UvIndex(1),
    windSpeed = Speed.fromKph(7.1),
    humidity = Humidity(100),
    visibility = AverageVisibility.fromKm(.5),
)

internal val LightRainHourForecast = ForecastHourState(
    id = 5L,
    time = "08:00",
    description = "Light Rain",
    iconId = R.drawable.ic_light_rain,
    temperature = Temperature.fromCelsius(13.7),
    feelsLikeTemperature = Temperature.fromCelsius(14.0),
    precipitation = Precipitation.fromMillimeters(4.0),
    uvIndex = UvIndex(3),
    windSpeed = Speed.fromKph(3.2),
    humidity = Humidity(100),
    visibility = AverageVisibility.fromKm(4.5),
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
