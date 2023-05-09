package com.francescsoftware.weathersample.interactor.weather.api

import com.francescsoftware.weathersample.interactor.weather.api.model.TodayWeather
import com.francescsoftware.weathersample.type.Either

/** Gets the current weather */
interface GetTodayWeatherInteractor {
    /**
     * Gets the current weather
     *
     * @param location - a [WeatherLocation] to get the weather for
     * @return an [Either] with today's weather
     */
    suspend operator fun invoke(location: WeatherLocation): Either<TodayWeather>
}
