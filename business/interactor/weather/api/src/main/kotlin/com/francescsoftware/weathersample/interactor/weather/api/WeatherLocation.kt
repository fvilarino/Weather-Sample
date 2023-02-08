package com.francescsoftware.weathersample.interactor.weather.api

sealed interface WeatherLocation {
    data class City(
        val name: String,
        val countryCode: String,
    ) : WeatherLocation

    data class Coordinates(
        val latitude: Double,
        val longitude: Double,
    ) : WeatherLocation
}
