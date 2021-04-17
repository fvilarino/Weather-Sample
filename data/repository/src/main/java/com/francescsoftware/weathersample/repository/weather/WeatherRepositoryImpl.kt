package com.francescsoftware.weathersample.repository.weather

import com.francescsoftware.weathersample.repository.safeApiCall
import com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastResponse
import com.francescsoftware.weathersample.repository.weather.model.today.TodayWeatherResponse
import com.francescsoftware.weathersample.type.Result
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService
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
