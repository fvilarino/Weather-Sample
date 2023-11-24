package com.francescsoftware.weathersample.data.repository.city.impl

import com.francescsoftware.weathersample.core.type.location.Coordinates
import com.francescsoftware.weathersample.data.repository.city.api.model.City
import com.francescsoftware.weathersample.data.repository.city.api.model.CitySearchResponse
import com.francescsoftware.weathersample.data.repository.city.api.model.Metadata
import com.francescsoftware.weathersample.data.repository.city.impl.model.CityModel
import com.francescsoftware.weathersample.data.repository.city.impl.model.CitySearchResponseModel
import com.francescsoftware.weathersample.data.repository.city.impl.model.MetadataModel

internal fun CitySearchResponseModel.toCityResponse(): CitySearchResponse = CitySearchResponse(
    metadata = metadata?.toCityMetadata() ?: Metadata(0, 0),
    cities = data?.toCities().orEmpty(),
)

internal fun MetadataModel.toCityMetadata(): Metadata = Metadata(
    currentOffset = currentOffset ?: 0,
    totalCount = totalCount ?: 0,
)

internal fun List<CityModel>.toCities(): List<City> = map { city -> city.toCity() }

internal fun CityModel.toCity(): City = City(
    id = id?.toLong() ?: 0L,
    city = city.orEmpty(),
    name = name.orEmpty(),
    region = region.orEmpty(),
    regionCode = regionCode.orEmpty(),
    country = country.orEmpty(),
    countryCode = countryCode.orEmpty(),
    coordinates = Coordinates(
        longitude = longitude ?: 0.0,
        latitude = latitude ?: 0.0,
    ),
)
