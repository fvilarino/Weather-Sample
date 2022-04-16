package com.francescsoftware.weathersample.feature.weather

import com.francescsoftware.weathersample.interactor.weather.api.TodayWeather
import com.francescsoftware.weathersample.lookup.api.StringLookup

internal fun TodayWeather.toWeatherCardState(stringLookup: StringLookup) =
    TodayWeatherCardState(
        temperature = main.temp.formatTemperature(stringLookup),
        minTemperature = main.tempMin.formatTemperature(stringLookup),
        maxTemperature = main.tempMax.formatTemperature(stringLookup),
        feelsLikeTemperature = main.feelsLike.formatTemperature(stringLookup),
        description = formatDescription(),
        windSpeed = wind.speed.formatWind(stringLookup),
        humidity = main.humidity.formatHumidity(stringLookup),
        pressure = main.pressure.formatPressure(stringLookup),
        visibility = visibility.formatVisibility(stringLookup),
        iconId = icon,
    )

private val TodayWeather.icon: Int
    get() = WeatherIcon.fromIconId(weather.icon).iconId

