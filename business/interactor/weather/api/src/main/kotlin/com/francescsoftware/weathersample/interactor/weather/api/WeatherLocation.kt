package com.francescsoftware.weathersample.interactor.weather.api

/** The location to get weather for */
sealed interface WeatherLocation {
    /**
     * A location specified as a city name and country code
     *
     * @property name - the city name
     * @property countryCode - the 2 digits country code
     */
    data class City(
        val name: String,
        val countryCode: String,
    ) : WeatherLocation

    /**
     * A location specified as latitude and longitude coordinates
     *
     * @property latitude - the location's latitude
     * @property longitude - the location's longitude
     */
    data class Coordinates(
        val latitude: Double,
        val longitude: Double,
    ) : WeatherLocation
}
