package com.francescsoftware.weathersample.cityrepository.api.model

/**
 * Contains pagination data for the response
 *
 * @property currentOffset current result offset
 * @property totalCount total number of result
 */
data class Metadata(
    val currentOffset: Int,
    val totalCount: Int,
)
