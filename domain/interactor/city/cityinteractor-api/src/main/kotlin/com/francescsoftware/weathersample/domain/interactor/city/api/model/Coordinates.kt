package com.francescsoftware.weathersample.domain.interactor.city.api.model

/**
 * Lat/Lon coordinates for a city
 *
 * @property latitude - city latitude
 * @property longitude - city longitude
 */
data class Coordinates(
    val latitude: Double,
    val longitude: Double,
)
