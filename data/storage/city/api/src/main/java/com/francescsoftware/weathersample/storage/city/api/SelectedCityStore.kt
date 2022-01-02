package com.francescsoftware.weathersample.storage.city.api

import kotlinx.coroutines.flow.Flow

data class SelectedCity(
    val name: String,
    val country: String,
    val countryCode :String,
)

interface SelectedCityStore {
    val city: Flow<SelectedCity>

    suspend fun setSelectedCity(
        name: String,
        country: String,
        countryCode: String,
    )
}
