package com.francescsoftware.weathersample.ui.feature.search.city.model

import javax.annotation.concurrent.Immutable

@Immutable
internal data class Coordinates(
    val latitude: Float,
    val longitude: Float,
)

@Immutable
internal data class CityResultModel(
    val id: Long,
    val favoriteId: Int,
    val name: String,
    val country: String,
    val countryCode: String,
    val coordinates: Coordinates,
) {
    val isFavorite: Boolean = favoriteId >= 0
}
