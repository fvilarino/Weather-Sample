package com.francescsoftware.weathersample.interactor.city.api

import com.francescsoftware.weathersample.type.Result
import java.io.IOException

data class Coordinates(
    val latitude: Double,
    val longitude: Double,
)

data class City(
    val id: Int,
    val name: String,
    val region: String,
    val regionCode: String,
    val country: String,
    val countryCode: String,
    val coordinates: Coordinates,
)

interface GetCitiesInteractor {
    suspend fun execute(prefix: String = "", limit: Int = 10): Result<List<City>>
}

class CitiesException : IOException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}
