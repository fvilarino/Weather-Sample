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
        when (location) {
            is WeatherLocation.City -> weatherService.getTodayWeather(
                query = formatCityQuery(location),
            )

            is WeatherLocation.Coordinates -> weatherService.getTodayWeather(
                query = formatCoordinates(location),
            )
        }
    }

    override suspend fun getForecast(
        location: WeatherLocation,
        days: Int,
    ): Either<ForecastResponse> =
        safeApiCall {
            when (location) {
                is WeatherLocation.City -> weatherService.getForecast(
                    query = formatCityQuery(location),
                    days = days,
                )

                is WeatherLocation.Coordinates -> weatherService.getForecast(
                    query = formatCoordinates(location),
                    days = days,
                )
            }
        }

    private fun formatCityQuery(location: WeatherLocation.City) =
        "${location.name},${location.countryCode}"

    private fun formatCoordinates(location: WeatherLocation.Coordinates) =
        "${location.latitude},${location.longitude}"
}
