package com.francescsoftware.weathersample.ui.feature.search.weather.viewmodel

import com.francescsoftware.weathersample.domain.interactor.weather.api.model.TodayWeather
import com.francescsoftware.weathersample.ui.shared.composable.weather.CurrentWeatherState
import com.francescsoftware.weathersample.ui.shared.weathericon.drawableId
import com.francescsoftware.weathersample.ui.shared.weathericon.weatherIconFromCode

internal fun TodayWeather.toWeatherCardState() =
    CurrentWeatherState(
        temperature = main.temperature,
        feelsLikeTemperature = main.feelsLike,
        precipitation = main.precipitation,
        uvIndex = main.uvIndex,
        description = main.formatDescription(),
        windSpeed = wind.speed,
        humidity = main.humidity,
        pressure = main.pressure,
        visibility = visibility,
        iconId = icon,
    )

private val TodayWeather.icon: Int
    get() = weatherIconFromCode(main.code).drawableId
