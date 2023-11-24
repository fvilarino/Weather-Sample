package com.francescsoftware.weathersample.data.repository.city.api.model

import com.francescsoftware.weathersample.core.type.location.Coordinates

/**
 * City response model
 *
 * @property id the city id
 * @property city the city name
 * @property name the city name
 * @property region the city region
 * @property regionCode the city region code
 * @property country the city country
 * @property countryCode the city country code
 * @property coordinates the city coordinates
 */
data class City(
    val id: Long,
    val city: String,
    val name: String,
    val region: String,
    val regionCode: String,
    val country: String,
    val countryCode: String,
    val coordinates: Coordinates,
)
