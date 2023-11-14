package com.francescsoftware.weathersample.domain.interactor.weather.api

import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.domain.interactor.foundation.Interactor
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.TodayWeather

/** Gets the current weather */
interface GetTodayWeatherInteractor : Interactor<GetTodayWeatherInteractor.Params, Either<TodayWeather>> {

    /**
     * Configuration parameters for the [GetTodayWeatherInteractor]
     *
     * @property location the [WeatherLocation] for the current weather
     */
    data class Params(val location: WeatherLocation)
}
