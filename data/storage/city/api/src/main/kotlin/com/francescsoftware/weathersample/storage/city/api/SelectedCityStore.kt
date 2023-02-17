package com.francescsoftware.weathersample.storage.city.api

import kotlinx.coroutines.flow.Flow

/**
 * Model for a selected city
 *
 * @property name - city name
 * @property country - city country
 * @property countryCode - city 2 digits country code
 */
data class SelectedCity(
    val name: String,
    val country: String,
    val countryCode: String,
)

/** Persistent storage for a selected cities */
interface SelectedCityStore {
    /** A [Flow] of the selected city */
    val city: Flow<SelectedCity>

    /**
     * Stores a [SelectedCity]
     *
     * @param name - city name
     * @param country - city country
     * @param countryCode - city 2 digits country code
     */
    suspend fun setSelectedCity(
        name: String,
        country: String,
        countryCode: String,
    )
}
