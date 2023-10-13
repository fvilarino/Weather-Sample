package com.francescsoftware.weathersample.domain.interactor.weather.api

import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.domain.interactor.foundation.Interactor
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.Forecast

/** Gets the forecast for the [WeatherLocation] */
interface GetForecastInteractor : Interactor<GetForecastInteractor.Params, Either<Forecast>> {

    data class Params(val location: WeatherLocation)
}
