package com.francescsoftware.weathersample.interactor.weather.api

import com.francescsoftware.weathersample.type.Result

data class TodayWeather(
    val weather: TodayWeatherItem,
    val main: TodayMain,
    val wind: TodayWind,
    val visibility: Int,
    val clouds: TodayClouds,
)

data class TodayWeatherItem(
    val icon: String,
    val description: String,
    val main: String,
)

data class TodayMain(
    val temp: Double,
    val tempMin: Double,
    val tempMax: Double,
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
)

data class TodayWind(
    val deg: Int,
    val speed: Double,
    val gust: Double,
)

data class TodayClouds(
    val all: Int,
)

interface GetTodayWeatherInteractor {
    suspend fun execute(location: WeatherLocation): Result<TodayWeather>
}
