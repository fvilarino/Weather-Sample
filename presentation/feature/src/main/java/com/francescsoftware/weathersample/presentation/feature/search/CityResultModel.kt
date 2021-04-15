package com.francescsoftware.weathersample.presentation.feature.search

data class CityResultModel(
    val id: Long,
    val name: CharSequence,
    val country: CharSequence,
    val countryCode: String,
    val coordinates: CharSequence,
)
