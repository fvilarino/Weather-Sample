package com.francescsoftware.weathersample.cityrepository.api

import com.francescsoftware.weathersample.cityrepository.api.model.CityItem
import com.francescsoftware.weathersample.cityrepository.api.model.CitySearchResponse
import com.francescsoftware.weathersample.cityrepository.api.model.Metadata
import com.francescsoftware.weathersample.type.Either
import java.io.IOException

private val metroVancouver = CityItem(
    country = "Canada",
    wikiDataId = "Q1061069",
    regionCode = "BC",
    city = "Metro Vancouver",
    countryCode = "CA",
    latitude = 49.2494,
    name = "Metro Vancouver",
    id = 140031,
    type = "CITY",
    region = "British Columbia",
    longitude = -122.98,
)

private val vancouver = CityItem(
    country = "Canada",
    wikiDataId = "Q24639",
    regionCode = "BC",
    city = "Vancouver",
    countryCode = "CA",
    latitude = 49.260833333,
    name = "Vancouver",
    id = 10841,
    type = "CITY",
    region = "British Columbia",
    longitude = -123.113888888,
)

private val westVancouver = CityItem(
    country = "Canada",
    wikiDataId = "Q991329",
    regionCode = "BC",
    city = "West Vancouver",
    countryCode = "CA",
    latitude = 49.3667,
    name = "West Vancouver",
    id = 11856,
    type = "CITY",
    region = "British Columbia",
    longitude = -123.167,
)

private val waVancouver = CityItem(
    country = "United States of America",
    wikiDataId = "Q234053",
    regionCode = "WA",
    city = "Vancouver",
    countryCode = "US",
    latitude = 45.631111111,
    name = "Vancouver",
    id = 128929,
    type = "CITY",
    region = "Washington",
    longitude = -122.671666666,
)

private val northVancouver = CityItem(
    country = "Canada",
    wikiDataId = "Q2000891",
    regionCode = "BC",
    city = "North Vancouver",
    countryCode = "CA",
    latitude = 49.31636,
    name = "North Vancouver",
    id = 3449259,
    type = "CITY",
    region = "British Columbia",
    longitude = -123.06934,
)

private val successResponse = CitySearchResponse(
    metadata = Metadata(currentOffset = 0, totalCount = 5),
    data = listOf(
        metroVancouver,
        northVancouver,
        vancouver,
        westVancouver,
        waVancouver
    ),
    links = null,
)

class FakeCityRepository : CityRepository {
    var success = true

    override suspend fun getCities(
        prefix: String,
        limit: Int,
    ): Either<CitySearchResponse> = if (success) {
        Either.Success(successResponse)
    } else {
        Either.Failure(IOException("Failed to parse data"))
    }
}
