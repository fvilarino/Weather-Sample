package com.francescsoftware.weathersample.cityrepository.impl

import com.francescsoftware.weathersample.cityrepository.api.model.City
import com.francescsoftware.weathersample.cityrepository.api.model.CitySearchResponse
import com.francescsoftware.weathersample.cityrepository.api.model.Coordinates
import com.francescsoftware.weathersample.cityrepository.api.model.Metadata
import com.francescsoftware.weathersample.cityrepository.impl.model.CityModel
import com.francescsoftware.weathersample.cityrepository.impl.model.CitySearchResponseModel
import com.francescsoftware.weathersample.cityrepository.impl.model.MetadataModel

internal fun CitySearchResponseModel.toCityResponse(): CitySearchResponse = CitySearchResponse(
    metadata = metadata?.toCityMetadata() ?: Metadata(0, 0),
    cities = data?.toCities().orEmpty()
)

internal fun MetadataModel.toCityMetadata(): Metadata = Metadata(
    currentOffset = currentOffset ?: 0,
    totalCount = totalCount ?: 0,
)

internal fun List<CityModel>.toCities(): List<City> = map { city -> city.toCity() }

internal fun CityModel.toCity(): City = City(
    id = id ?: 0,
    city = city.orEmpty(),
    name = name.orEmpty(),
    region = region.orEmpty(),
    regionCode = regionCode.orEmpty(),
    country = country.orEmpty(),
    countryCode = countryCode.orEmpty(),
    coordinates = Coordinates(
        longitude = longitude ?: 0.0,
        latitude = latitude ?: 0.0,
    )
)
