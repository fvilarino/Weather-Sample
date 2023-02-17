package com.francescsoftware.weathersample.weatherrepository.api

import com.francescsoftware.weathersample.type.Either
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.ForecastResponse
import com.francescsoftware.weathersample.weatherrepository.api.model.today.TodayWeatherResponse

/** The location to get weather for */
sealed interface WeatherLocation {
    /**
     * A location specified as a city name and country code
     *
     * @property name - the city name
     * @property countryCode - the 2 digits country code
     */
    data class City(
        val name: String,
        val countryCode: String,
    ) : WeatherLocation

    /**
     * A location specified as latitude and longitude coordinates
     *
     * @property latitude - the location's latitude
     * @property longitude - the location's longitude
     */
    data class Coordinates(
        val latitude: Double,
        val longitude: Double,
    ) : WeatherLocation
}

/** Weather repository */
interface WeatherRepository {
    /**
     * Gets the current weather
     *
     * @param location - the [WeatherLocation] to get the weather for
     * @return an [Either] with [TodayWeatherResponse] for the [WeatherLocation]
     */
    suspend fun getTodayWeather(location: WeatherLocation): Either<TodayWeatherResponse>

    /**
     * Gets the weather forecast
     *
     * @param location - the [WeatherLocation] to get the weather for
     * @param days - number of days for the forecast
     * @return an [Either] with the [ForecastResponse]
     */
    suspend fun getForecast(location: WeatherLocation, days: Int = 7): Either<ForecastResponse>
}
