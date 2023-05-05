package com.francescsoftware.weathersample.cityrepository.api.model

/**
 * Coordinates
 *
 * @property longitude longitude, in range -180 to 180
 * @property latitude latitude, in range -90 to 90
 */
data class Coordinates(
    val longitude: Double,
    val latitude: Double,
)
