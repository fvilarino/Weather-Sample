package com.francescsoftware.weathersample.interactor.city.api.model

/**
 * Cities result
 *
 * @property metadata - response metadata (offset and total count)
 * @property cities - list of [City]
 */
data class Cities(
    val metadata: Metadata,
    val cities: List<City>,
)
