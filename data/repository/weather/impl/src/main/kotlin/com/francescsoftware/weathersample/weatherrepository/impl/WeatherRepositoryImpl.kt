package com.francescsoftware.weathersample.weatherrepository.impl

import com.francescsoftware.weathersample.network.safeApiCall
import com.francescsoftware.weathersample.type.Either
import com.francescsoftware.weathersample.weatherrepository.api.WeatherLocation
import com.francescsoftware.weathersample.weatherrepository.api.WeatherRepository
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.ForecastResponse
import com.francescsoftware.weathersample.weatherrepository.api.model.today.TodayWeatherResponse
import javax.inject.Inject

internal class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
) : WeatherRepository {

    override suspend fun getTodayWeather(
        location: WeatherLocation
    ): Either<TodayWeatherResponse> = safeApiCall {
        weatherService.getTodayWeather(
            query = location.formattedQuery,
        )
    }

    override suspend fun getForecast(
        location: WeatherLocation,
        days: Int,
    ): Either<ForecastResponse> = safeApiCall {
        weatherService.getForecast(
            query = location.formattedQuery,
            days = days,
        )
    }

    private val WeatherLocation.formattedQuery: String
        get() = when (this) {
            is WeatherLocation.City -> "$name,$countryCode"
            is WeatherLocation.Coordinates -> "$latitude,$longitude"
        }
}
