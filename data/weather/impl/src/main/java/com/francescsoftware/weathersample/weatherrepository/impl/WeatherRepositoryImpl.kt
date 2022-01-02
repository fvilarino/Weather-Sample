package com.francescsoftware.weathersample.weatherrepository.impl

import com.francescsoftware.weathersample.network.safeApiCall
import com.francescsoftware.weathersample.type.Result
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
    ): Result<TodayWeatherResponse> = safeApiCall {
        when (location) {
            is WeatherLocation.City -> weatherService.getTodayWeather(
                cityAndCountry = formatCityQuery(location)
            )
            is WeatherLocation.Coordinates -> weatherService.getTodayWeather(
                latitude = location.latitude,
                longitude = location.longitude,
            )
        }
    }

    override suspend fun getForecast(location: WeatherLocation): Result<ForecastResponse> =
        safeApiCall {
            when (location) {
                is WeatherLocation.City -> weatherService.getForecast(
                    cityAndCountry = formatCityQuery(location)
                )
                is WeatherLocation.Coordinates -> weatherService.getForecast(
                    latitude = location.latitude,
                    longitude = location.longitude,
                )
            }
        }

    private fun formatCityQuery(location: WeatherLocation.City) =
        "${location.name},${location.countryCode}"
}
