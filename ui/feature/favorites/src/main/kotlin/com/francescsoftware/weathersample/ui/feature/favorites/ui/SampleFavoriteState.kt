package com.francescsoftware.weathersample.ui.feature.favorites.ui

import com.francescsoftware.weathersample.shared.assets.R
import com.francescsoftware.weathersample.shared.composable.weather.CurrentWeatherState
import com.francescsoftware.weathersample.shared.composable.weather.ForecastHeaderState
import com.francescsoftware.weathersample.shared.composable.weather.ForecastHourState
import kotlinx.collections.immutable.persistentListOf

private val PartlyCloudyHourForecast = ForecastHourState(
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

private val SunnyHourForecast = ForecastHourState(
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

private val HeavyRainHourForecast = ForecastHourState(
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

private val SnowHourForecast = ForecastHourState(
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

private val LightRainHourForecast = ForecastHourState(
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

internal val VancouverFavoriteCardState = FavoriteCardState(
    city = City(
        favoriteId = 1,
        name = "Vancouver",
        countryCode = "CA",
    ),
    current = CurrentWeatherState(
        temperature = "16.4°C",
        feelsLikeTemperature = "14.3°C",
        precipitation = "10",
        uvIndex = "5",
        description = "Partly cloudy",
        iconId = R.drawable.ic_partly_cloudy,
        windSpeed = "4.3kph",
        humidity = "54%",
        pressure = "1024mb",
        visibility = "10 km",
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
