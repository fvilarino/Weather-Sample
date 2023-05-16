package com.francescsoftware.weathersample.feature.city.model

internal data class CityResultModel(
    val id: Long,
    val favoriteId: Int,
    val name: CharSequence,
    val country: CharSequence,
    val countryCode: String,
    val coordinates: CharSequence,
) {
    val isFavorite: Boolean
        get() = favoriteId >= 0
}
