package com.francescsoftware.weathersample.data.repository.weather.api

import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.data.repository.weather.api.model.forecast.ForecastResponse
import com.francescsoftware.weathersample.data.repository.weather.api.model.today.TodayWeatherResponse

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
