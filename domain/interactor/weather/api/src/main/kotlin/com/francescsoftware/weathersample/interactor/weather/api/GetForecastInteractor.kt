package com.francescsoftware.weathersample.interactor.weather.api

import com.francescsoftware.weathersample.interactor.weather.api.model.Forecast
import com.francescsoftware.weathersample.type.Either

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
