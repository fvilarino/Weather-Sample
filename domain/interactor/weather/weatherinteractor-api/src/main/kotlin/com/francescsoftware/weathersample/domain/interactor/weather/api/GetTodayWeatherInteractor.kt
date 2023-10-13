package com.francescsoftware.weathersample.domain.interactor.weather.api

import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.domain.interactor.foundation.Interactor
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.TodayWeather

/** Gets the current weather */
interface GetTodayWeatherInteractor : Interactor<GetTodayWeatherInteractor.Params, Either<TodayWeather>> {

    data class Params(val location: WeatherLocation)
}
