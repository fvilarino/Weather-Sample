package com.francescsoftware.weathersample.ui.shared.route

/**
 * A model class for the selected city
 *
 * @property name - the city name
 * @property country - the country the city belongs to
 * @property countryCode - the 2 digits country code for the country the city belongs to
 */
data class SelectedCity(
    val name: String,
    val country: String,
    val countryCode: String,
)
