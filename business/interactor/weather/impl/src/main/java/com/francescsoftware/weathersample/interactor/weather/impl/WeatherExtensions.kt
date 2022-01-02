package com.francescsoftware.weathersample.interactor.weather.impl

import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.weatherrepository.api.WeatherLocation as RepositoryLocation

internal fun WeatherLocation.toRepositoryLocation() = when (this) {
    is WeatherLocation.City -> RepositoryLocation.City(
        name = name,
        countryCode = countryCode,
    )
    is WeatherLocation.Coordinates -> RepositoryLocation.Coordinates(
        latitude = latitude,
        longitude = longitude,
    )
}
