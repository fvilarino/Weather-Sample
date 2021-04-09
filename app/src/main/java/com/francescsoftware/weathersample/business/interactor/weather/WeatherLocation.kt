package com.francescsoftware.weathersample.business.interactor.weather

sealed class WeatherLocation {
    data class City(
        val name: String,
        val countryCode: String,
    ) : WeatherLocation()

    data class Coordinates(
        val latitude: Double,
        val longitude: Double,
    ) : WeatherLocation()
}
