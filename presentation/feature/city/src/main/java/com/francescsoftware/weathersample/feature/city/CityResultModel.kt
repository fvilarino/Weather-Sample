package com.francescsoftware.weathersample.feature.city

data class CityResultModel(
    val id: Long,
    val name: CharSequence,
    val country: CharSequence,
    val countryCode: String,
    val coordinates: CharSequence,
)
