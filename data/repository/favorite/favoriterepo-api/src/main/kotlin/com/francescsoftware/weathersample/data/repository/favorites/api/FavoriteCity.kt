package com.francescsoftware.weathersample.data.repository.favorites.api

/**
 * A favorite city
 *
 * @property cityId the unique city id
 * @property name the city name
 * @property countryCode the city country code
 */
data class FavoriteCity(
    val cityId: Long,
    val name: String,
    val countryCode: String,
)
