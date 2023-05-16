package com.francescsoftware.weathersample.domain.interactor.weather.api

import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.Forecast

/** Gets the forecast for the [WeatherLocation] */
interface GetForecastInteractor {
    /**
     * Gets the [Forecast] for the [WeatherLocation]
     *
     * @param location - the [WeatherLocation] to get the forecast for
     * @return an [Either] with the [Forecast] for the [WeatherLocation]
     */
    suspend operator fun invoke(location: WeatherLocation): Either<Forecast>
}
