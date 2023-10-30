package com.francescsoftware.weathersample.domain.interactor.city.api.model

/**
 * Model for a favorite city
 *
 * @property cityId unique city id
 * @property name city name
 * @property countryCode 2 digit city country code
 */
data class FavoriteCity(
    val cityId: Long,
    val name: String,
    val countryCode: String,
)
