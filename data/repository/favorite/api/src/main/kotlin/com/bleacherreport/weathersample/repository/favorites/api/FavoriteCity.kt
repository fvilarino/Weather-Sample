package com.bleacherreport.weathersample.repository.favorites.api

/**
 * A favorite city
 *
 * @property id the city id
 * @property name the city name
 * @property countryCode the city country code
 */
data class FavoriteCity(
    val id: Int,
    val name: String,
    val countryCode: String,
)
