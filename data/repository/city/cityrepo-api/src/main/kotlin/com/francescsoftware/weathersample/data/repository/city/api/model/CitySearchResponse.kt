package com.francescsoftware.weathersample.data.repository.city.api.model

/**
 * Cities response
 *
 * @property metadata pagination data
 * @property cities list of cities
 */
data class CitySearchResponse(
    val metadata: Metadata,
    val cities: List<City>,
)
