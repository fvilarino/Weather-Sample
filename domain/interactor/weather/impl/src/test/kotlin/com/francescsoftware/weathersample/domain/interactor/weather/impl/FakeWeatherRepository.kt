package com.francescsoftware.weathersample.domain.interactor.weather.impl

import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.data.repository.weather.api.WeatherException
import com.francescsoftware.weathersample.data.repository.weather.api.WeatherLocation
import com.francescsoftware.weathersample.data.repository.weather.api.WeatherRepository
import com.francescsoftware.weathersample.data.repository.weather.api.model.forecast.ForecastResponse
import com.francescsoftware.weathersample.data.repository.weather.api.model.today.TodayWeatherResponse

internal class FakeWeatherRepository : WeatherRepository {
    var todayResponse: TodayWeatherResponse? = null
    var forecastResponse: ForecastResponse? = null
    var networkError: Boolean = false
    var lastLocation: WeatherLocation? = null

    override suspend fun getTodayWeather(location: WeatherLocation): Either<TodayWeatherResponse> {
        lastLocation = location
        return if (networkError) {
            Either.Failure(WeatherException("Failed to load weather"))
        } else {
            Either.Success(todayResponse!!)
        }
    }

    override suspend fun getForecast(
        location: WeatherLocation,
        days: Int
    ): Either<ForecastResponse> {
        lastLocation = location
        return if (networkError) {
            Either.Failure(WeatherException("Failed to load weather"))
        } else {
            Either.Success(forecastResponse!!)
        }
    }
}
