package com.francescsoftware.weathersample.interactor.city.api

import com.francescsoftware.weathersample.type.Either

private val metroVancouver = City(
    country = "Canada",
    regionCode = "BC",
    countryCode = "CA",
    name = "Metro Vancouver",
    id = 140031,
    region = "British Columbia",
    coordinates = Coordinates(
        latitude = 49.2494,
        longitude = -122.98,
    )
)

private val vancouver = City(
    country = "Canada",
    regionCode = "BC",
    countryCode = "CA",
    name = "Vancouver",
    id = 10841,
    region = "British Columbia",
    coordinates = Coordinates(
        latitude = 49.260833333,
        longitude = -123.113888888,
    )
)

private val westVancouver = City(
    country = "Canada",
    regionCode = "BC",
    countryCode = "CA",
    name = "West Vancouver",
    id = 11856,
    region = "British Columbia",
    coordinates = Coordinates(
        latitude = 49.3667,
        longitude = -123.167,
    )
)

private val waVancouver = City(
    country = "United States of America",
    regionCode = "WA",
    countryCode = "US",
    name = "Vancouver",
    id = 128929,
    region = "Washington",
    coordinates = Coordinates(
        latitude = 45.631111111,
        longitude = -122.671666666,
    )
)

private val northVancouver = City(
    country = "Canada",
    regionCode = "BC",
    countryCode = "CA",
    name = "North Vancouver",
    id = 3449259,
    region = "British Columbia",
    coordinates = Coordinates(
        latitude = 49.31636,
        longitude = -123.06934,
    )
)

class FakeGetCitiesInteractor : GetCitiesInteractor {
    var success = true

    override suspend fun execute(prefix: String, limit: Int): Either<List<City>> = if (success) {
        Either.Success(
            listOf(
                metroVancouver,
                northVancouver,
                vancouver,
                westVancouver,
                waVancouver
            )
        )
    } else {
        Either.Failure(
            CitiesException("Failed to parse data")
        )
    }
}
