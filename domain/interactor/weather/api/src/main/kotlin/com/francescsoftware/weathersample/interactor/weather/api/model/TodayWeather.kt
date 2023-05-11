package com.francescsoftware.weathersample.interactor.weather.api.model

/**
 * Weather for today
 *
 * @property main - main weather attributes
 * @property wind - wind speed
 * @property visibility - visibility in meters
 * @property clouds - cloud cover
 */
data class TodayWeather(
    val main: TodayMain,
    val wind: TodayWind,
    val visibility: Int,
    val clouds: TodayClouds,
)