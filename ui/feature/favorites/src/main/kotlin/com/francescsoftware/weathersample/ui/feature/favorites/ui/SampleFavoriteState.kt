@file: Suppress("MagicNumber")

package com.francescsoftware.weathersample.ui.feature.favorites.ui

import com.francescsoftware.weathersample.core.type.weather.AverageVisibility
import com.francescsoftware.weathersample.core.type.weather.Humidity
import com.francescsoftware.weathersample.core.type.weather.Precipitation
import com.francescsoftware.weathersample.core.type.weather.Pressure
import com.francescsoftware.weathersample.core.type.weather.Speed
import com.francescsoftware.weathersample.core.type.weather.Temperature
import com.francescsoftware.weathersample.core.type.weather.UvIndex
import com.francescsoftware.weathersample.ui.shared.assets.R
import com.francescsoftware.weathersample.ui.shared.composable.weather.CurrentWeatherState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHeaderState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHourState
import kotlinx.collections.immutable.persistentListOf

private val PartlyCloudyHourForecast = ForecastHourState(
    id = 1L,
    header = "02:00 - Scattered Clouds",
    iconId = R.drawable.ic_partly_cloudy,
    temperature = Temperature.fromCelsius(16.4),
    feelsLikeTemperature = Temperature.fromCelsius(15.5),
    precipitation = Precipitation.fromMillimeters(0.0),
    uvIndex = UvIndex(3),
    windSpeed = Speed.fromKph(5.4),
    humidity = Humidity(48),
    visibility = AverageVisibility.fromKm(3.0),
)

private val SunnyHourForecast = ForecastHourState(
    id = 2L,
    header = "14:00 - Sunny",
    iconId = R.drawable.ic_sunny,
    temperature = Temperature.fromCelsius(18.7),
    feelsLikeTemperature = Temperature.fromCelsius(17.5),
    precipitation = Precipitation.fromMillimeters(9.5),
    uvIndex = UvIndex(0),
    windSpeed = Speed.fromKph(14.1),
    humidity = Humidity(75),
    visibility = AverageVisibility.fromKm(2.5),
)

private val HeavyRainHourForecast = ForecastHourState(
    id = 3L,
    header = "06:00 - Heavy Rain",
    iconId = R.drawable.ic_heavy_rain,
    temperature = Temperature.fromCelsius(6.2),
    feelsLikeTemperature = Temperature.fromCelsius(5.0),
    precipitation = Precipitation.fromMillimeters(120.0),
    uvIndex = UvIndex(1),
    windSpeed = Speed.fromKph(0.2),
    humidity = Humidity(100),
    visibility = AverageVisibility.fromKm(1.0),
)

private val SnowHourForecast = ForecastHourState(
    id = 4L,
    header = "07:00 - Snow",
    iconId = R.drawable.ic_snow,
    temperature = Temperature.fromCelsius(3.4),
    feelsLikeTemperature = Temperature.fromCelsius(-5.5),
    precipitation = Precipitation.fromMillimeters(15.0),
    uvIndex = UvIndex(1),
    windSpeed = Speed.fromKph(7.1),
    humidity = Humidity(100),
    visibility = AverageVisibility.fromKm(.5),
)

private val LightRainHourForecast = ForecastHourState(
    id = 5L,
    header = "08:00 - Light Rain",
    iconId = R.drawable.ic_light_rain,
    temperature = Temperature.fromCelsius(13.7),
    feelsLikeTemperature = Temperature.fromCelsius(14.0),
    precipitation = Precipitation.fromMillimeters(4.0),
    uvIndex = UvIndex(3),
    windSpeed = Speed.fromKph(3.2),
    humidity = Humidity(100),
    visibility = AverageVisibility.fromKm(4.5),
)

internal val VancouverFavoriteCardState = FavoriteCardState(
    city = City(
        favoriteId = 1,
        name = "Vancouver",
        countryCode = "CA",
    ),
    current = CurrentWeatherState(
        temperature = Temperature.fromCelsius(16.4),
        feelsLikeTemperature = Temperature.fromCelsius(14.3),
        precipitation = Precipitation.fromMillimeters(10.0),
        uvIndex = UvIndex(5),
        description = "Partly cloudy",
        iconId = R.drawable.ic_partly_cloudy,
        windSpeed = Speed.fromKph(4.3),
        humidity = Humidity(54),
        pressure = Pressure.fromMillibars(1024.0),
        visibility = AverageVisibility.fromKm(10.0),
    ),
    forecast = persistentListOf(
        ForecastDayState(
            header = ForecastHeaderState(
                id = "header",
                date = "Aug 18, 2022",
                sunrise = "5:44am",
                sunset = "8:51pm",
            ),
            forecast = persistentListOf(
                PartlyCloudyHourForecast,
                SunnyHourForecast,
                HeavyRainHourForecast,
                SnowHourForecast,
                LightRainHourForecast,
            ),
        ),
    ),
)

internal val BarcelonaFavoriteCardState = VancouverFavoriteCardState.copy(
    city = City(
        favoriteId = 2,
        name = "Barcelona",
        countryCode = "ES",
    )
)

internal val LondonFavoriteCardState = VancouverFavoriteCardState.copy(
    city = City(
        favoriteId = 3,
        name = "London",
        countryCode = "GB",
    )
)
