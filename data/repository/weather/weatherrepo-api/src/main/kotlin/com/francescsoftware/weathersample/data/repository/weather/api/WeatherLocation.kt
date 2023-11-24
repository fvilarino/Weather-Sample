package com.francescsoftware.weathersample.data.repository.weather.api

import com.francescsoftware.weathersample.core.type.location.Coordinates

/** The location to get weather for */
sealed interface WeatherLocation {
    /**
     * A location specified as a city name and country code
     *
     * @property name the city name
     * @property countryCode the 2 digits country code
     */
    data class City(
        val name: String,
        val countryCode: String,
    ) : WeatherLocation

    /**
     * A location specified as latitude and longitude coordinates
     *
     * @property coordinates the weather coordinates
     */
    data class Location(
        val coordinates: Coordinates,
    ) : WeatherLocation
}
