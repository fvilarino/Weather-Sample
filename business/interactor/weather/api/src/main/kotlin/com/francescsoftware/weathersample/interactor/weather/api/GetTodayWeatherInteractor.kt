package com.francescsoftware.weathersample.interactor.weather.api

import com.francescsoftware.weathersample.type.Either

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

/**
 * Main weather attributes
 *
 * @property description - precipitation in mm
 * @property code - weather code
 * @property temp - temperature
 * @property feelsLike - feels like temperature
 * @property humidity - humidity percent
 * @property pressure - atmospheric pressure
 * @property precipitation - precipitation in mm
 * @property uvIndex - UV index
 */
data class TodayMain(
    val description: String,
    val code: Int,
    val temp: Double,
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val precipitation: Int,
    val uvIndex: Int,
)

/**
 * Wind
 *
 * @property direction - wind direction
 * @property speed - wind speed
 * @property gust - gust speed
 */
data class TodayWind(
    val direction: String,
    val speed: Double,
    val gust: Double,
)

/**
 * Weather cloud cover
 *
 * @property all - cloud cover
 */
data class TodayClouds(
    val all: Int,
)

/** Gets the current weather */
interface GetTodayWeatherInteractor {
    /**
     * Gets the current weather
     *
     * @param location - a [WeatherLocation] to get the weather for
     * @return an [Either] with today's weather
     */
    suspend fun execute(location: WeatherLocation): Either<TodayWeather>
}
