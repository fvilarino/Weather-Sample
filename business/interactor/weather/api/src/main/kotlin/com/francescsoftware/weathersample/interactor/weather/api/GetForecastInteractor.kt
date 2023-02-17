package com.francescsoftware.weathersample.interactor.weather.api

import com.francescsoftware.weathersample.type.Either
import java.util.Date

/**
 * Weather forecast for multiple days
 *
 * @property items - a [List] of [ForecastDay]
 */
data class Forecast(
    val items: List<ForecastDay>,
)

/**
 * Weather forecast for a day
 *
 * @property date - the date of each forecast
 * @property sunrise - the sunrise time
 * @property sunset - the sunset time
 * @property entries - a [List] of [ForecastEntry] for this day
 */
data class ForecastDay(
    val date: Date,
    val sunrise: String,
    val sunset: String,
    val entries: List<ForecastEntry>,
)

/**
 * A weather forecast
 *
 * @property date - the [Date] this forecast is for
 * @property description - forecast description
 * @property iconCode - the code for the icon representing this forecast
 * @property temperature - the temperature
 * @property feelsLikeTemperature - the feels like temperature
 * @property precipitation - the amount of precipitation
 * @property windSpeed - the wind speed
 * @property uvIndex - the UV index
 * @property humidityPercent - the humidity percent
 * @property visibility - the visibility in meters
 */
data class ForecastEntry(
    val date: Date,
    val description: String,
    val iconCode: Int,
    val temperature: Double,
    val feelsLikeTemperature: Double,
    val precipitation: Int,
    val windSpeed: Double,
    val uvIndex: Int,
    val humidityPercent: Int,
    val visibility: Int,
)

/** Gets the forecast for the [WeatherLocation] */
interface GetForecastInteractor {
    /**
     * Gets the [Forecast] for the [WeatherLocation]
     *
     * @param location - the [WeatherLocation] to get the forecast for
     * @return an [Either] with the [Forecast] for the [WeatherLocation]
     */
    suspend fun execute(location: WeatherLocation): Either<Forecast>
}
