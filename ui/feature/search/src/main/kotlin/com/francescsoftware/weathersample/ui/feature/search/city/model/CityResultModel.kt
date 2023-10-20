package com.francescsoftware.weathersample.ui.feature.search.city.model

import androidx.compose.runtime.Immutable

@Immutable
data class Coordinates(
    val latitude: Float,
    val longitude: Float,
)

@Immutable
data class CityResultModel(
    val cityId: Long,
    val name: String,
    val country: String,
    val countryCode: String,
    val coordinates: Coordinates,
) {
    val isFavorite: Boolean = cityId >= 0L
}
