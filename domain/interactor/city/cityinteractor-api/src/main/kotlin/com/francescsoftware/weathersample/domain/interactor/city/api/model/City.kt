package com.francescsoftware.weathersample.domain.interactor.city.api.model

import com.francescsoftware.weathersample.core.type.location.Coordinates

/**
 * Model for a city
 *
 * @property id unique id
 * @property name city name
 * @property region city region
 * @property regionCode city regin code
 * @property country city country
 * @property countryCode 2 digit city country code
 * @property coordinates the [Coordinates] for the city
 */
data class City(
    val id: Long,
    val name: String,
    val region: String,
    val regionCode: String,
    val country: String,
    val countryCode: String,
    val coordinates: Coordinates,
)
