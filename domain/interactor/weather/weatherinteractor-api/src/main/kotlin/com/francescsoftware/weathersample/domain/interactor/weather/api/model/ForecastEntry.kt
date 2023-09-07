package com.francescsoftware.weathersample.domain.interactor.weather.api.model

import com.francescsoftware.weathersample.core.type.weather.AverageVisibility
import com.francescsoftware.weathersample.core.type.weather.Humidity
import com.francescsoftware.weathersample.core.type.weather.Precipitation
import com.francescsoftware.weathersample.core.type.weather.Speed
import com.francescsoftware.weathersample.core.type.weather.Temperature
import com.francescsoftware.weathersample.core.type.weather.UvIndex
import java.time.ZonedDateTime

/**
 * A weather forecast
 *
 * @property zonedDateTime - the [ZonedDateTime] this forecast is for
 * @property description - forecast description
 * @property iconCode - the code for the icon representing this forecast
 * @property temperature - the temperature
 * @property feelsLike - the feels like temperature
 * @property precipitation - the amount of precipitation
 * @property windSpeed - the wind speed
 * @property uvIndex - the UV index
 * @property humidity - the humidity percent
 * @property visibility - the visibility in meters
 */
data class ForecastEntry(
    val zonedDateTime: ZonedDateTime,
    val description: String,
    val iconCode: Int,
    val temperature: Temperature,
    val feelsLike: Temperature,
    val precipitation: Precipitation,
    val windSpeed: Speed,
    val uvIndex: UvIndex,
    val humidity: Humidity,
    val visibility: AverageVisibility,
)
