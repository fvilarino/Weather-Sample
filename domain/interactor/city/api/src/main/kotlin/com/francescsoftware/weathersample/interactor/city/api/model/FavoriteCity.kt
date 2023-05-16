package com.francescsoftware.weathersample.interactor.city.api.model

/**
 * Model for a favorite city
 *
 * @property id - unique id
 * @property name - city name
 * @property countryCode - 2 digit city country code
 */
data class FavoriteCity(
    val id: Int,
    val name: String,
    val countryCode: String,
)
