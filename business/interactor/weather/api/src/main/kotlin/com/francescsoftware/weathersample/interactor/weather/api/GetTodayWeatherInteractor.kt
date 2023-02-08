package com.francescsoftware.weathersample.interactor.weather.api

import com.francescsoftware.weathersample.type.Either

data class TodayWeather(
    val main: TodayMain,
    val wind: TodayWind,
    val visibility: Int,
    val clouds: TodayClouds,
)

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

data class TodayWind(
    val direction: String,
    val speed: Double,
    val gust: Double,
)

data class TodayClouds(
    val all: Int,
)

interface GetTodayWeatherInteractor {
    suspend fun execute(location: WeatherLocation): Either<TodayWeather>
}
