package com.francescsoftware.weathersample.interactor.city.api

import com.francescsoftware.weathersample.type.Either
import java.io.IOException

/**
 * Lat/Lon coordinates for a city
 *
 * @property latitude - city latitude
 * @property longitude - city longitude
 */
data class Coordinates(
    val latitude: Double,
    val longitude: Double,
)

/**
 * Model for a city
 *
 * @property id - unique id
 * @property name - city name
 * @property region - city region
 * @property regionCode - city regin code
 * @property country - city country
 * @property countryCode - 2 digit city country code
 * @property coordinates - the [Coordinates] for the city
 */
data class City(
    val id: Int,
    val name: String,
    val region: String,
    val regionCode: String,
    val country: String,
    val countryCode: String,
    val coordinates: Coordinates,
)

/** Gets a list of cities matching the prefix */
interface GetCitiesInteractor {
    /**
     * Gets a list of cities matching the [prefix]
     *
     * @param prefix - prefix to filter cities
     * @param limit - max number of results to return
     * @return an [Either] with a [List] of [City]
     */
    suspend fun execute(prefix: String = "", limit: Int = 10): Either<List<City>>
}

/** Exception thrown when loading cities */
class CitiesException : IOException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}
