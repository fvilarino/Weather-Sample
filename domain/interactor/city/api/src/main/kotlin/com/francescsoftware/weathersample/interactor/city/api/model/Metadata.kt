package com.francescsoftware.weathersample.interactor.city.api.model

/**
 * Cities response metadata for pagination
 *
 * @property offset this response's offset
 * @property totalCount total number of results
 */
data class Metadata(
    val offset: Int,
    val totalCount: Int,
)
