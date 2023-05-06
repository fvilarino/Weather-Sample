package com.francescsoftware.weathersample.feature.weather.viewmodel

import com.francescsoftware.weathersample.interactor.weather.api.TodayWeather
import com.francescsoftware.weathersample.lookup.api.StringLookup

internal fun TodayWeather.toWeatherCardState(stringLookup: StringLookup) =
    TodayWeatherCardState(
        temperature = main.temp.formatTemperature(stringLookup),
        feelsLikeTemperature = main.feelsLike.formatTemperature(stringLookup),
        precipitation = main.precipitation.toString(),
        uvIndex = main.uvIndex.toString(),
        description = main.formatDescription(),
        windSpeed = wind.speed.formatWind(stringLookup),
        humidity = main.humidity.formatHumidity(stringLookup),
        pressure = main.pressure.formatPressure(stringLookup),
        visibility = visibility.formatVisibility(stringLookup),
        iconId = icon,
    )

private val TodayWeather.icon: Int
    get() = weatherIconFromCode(main.code).drawableId