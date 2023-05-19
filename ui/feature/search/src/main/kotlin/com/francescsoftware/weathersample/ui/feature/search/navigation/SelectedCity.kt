package com.francescsoftware.weathersample.ui.feature.search.navigation

/**
 * A model class for the selected city
 *
 * @property name - the city name
 * @property countryCode - the 2 digits country code for the country the city belongs to
 */
internal data class SelectedCity(
    val name: String,
    val countryCode: String,
)
