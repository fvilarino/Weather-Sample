package com.francescsoftware.weathersample.ui.feature.search.weather.viewmodel

import com.francescsoftware.weathersample.domain.interactor.weather.api.model.TodayWeather
import com.francescsoftware.weathersample.ui.shared.composable.weather.CurrentWeatherState
import com.francescsoftware.weathersample.ui.shared.format.weather.drawableId
import com.francescsoftware.weathersample.ui.shared.format.weather.format
import com.francescsoftware.weathersample.ui.shared.format.weather.weatherIconFromCode
import com.francescsoftware.weathersample.ui.shared.lookup.api.StringLookup
import kotlin.math.roundToInt

internal fun TodayWeather.toWeatherCardState(stringLookup: StringLookup) =
    CurrentWeatherState(
        temperature = main.temperature.format(stringLookup),
        feelsLikeTemperature = main.feelsLike.format(stringLookup),
        precipitation = main.precipitation.millimeters.roundToInt().toString(),
        uvIndex = main.uvIndex.toString(),
        description = main.formatDescription(),
        windSpeed = wind.speed.format(stringLookup),
        humidity = main.humidity.format(stringLookup),
        pressure = main.pressure.format(stringLookup),
        visibility = visibility.format(stringLookup),
        iconId = icon,
    )

private val TodayWeather.icon: Int
    get() = weatherIconFromCode(main.code).drawableId
