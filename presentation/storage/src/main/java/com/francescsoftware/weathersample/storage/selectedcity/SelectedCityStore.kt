package com.francescsoftware.weathersample.storage.selectedcity

import com.francescsoftware.weathersample.presentation.storage.SelectedCity
import kotlinx.coroutines.flow.Flow

interface SelectedCityStore {
    val city: Flow<SelectedCity>

    suspend fun setSelectedCity(
        name: String,
        country: String,
        countryCode: String,
    )
}
