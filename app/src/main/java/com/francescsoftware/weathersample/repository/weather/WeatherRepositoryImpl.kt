package com.francescsoftware.weathersample.repository.weather

import com.francescsoftware.weathersample.repository.safeApiCall
import com.francescsoftware.weathersample.repository.weather.model.TodayWeatherResponse
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService
) : WeatherRepository {

    override suspend fun getTodayWeather(
        location: WeatherLocation
    ): Result<TodayWeatherResponse> = safeApiCall {
        when (location) {
            is WeatherLocation.City -> weatherService.getTodayWeather(
                cityAndCountry = "${location.name},${location.countryCode}"
            )
            is WeatherLocation.Coordinates -> weatherService.getTodayWeather(
                latitude = location.latitude,
                longitude = location.longitude,
            )
        }
    }
}
