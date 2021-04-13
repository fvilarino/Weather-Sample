package com.francescsoftware.weathersample.interactor.weather

import com.francescsoftware.weathersample.repository.weather.WeatherLocation as RepositoryLocation

internal fun WeatherLocation.toRepositoryLocation() = when (this) {
    is WeatherLocation.City -> RepositoryLocation.City(
        name = name,
        countryCode = countryCode
    )
    is WeatherLocation.Coordinates -> RepositoryLocation.Coordinates(
        latitude = latitude,
        longitude = longitude,
    )
}
