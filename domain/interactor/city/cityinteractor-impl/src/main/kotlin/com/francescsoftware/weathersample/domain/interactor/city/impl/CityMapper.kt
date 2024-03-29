package com.francescsoftware.weathersample.domain.interactor.city.impl

import com.francescsoftware.weathersample.data.repository.city.api.model.CitySearchResponse
import com.francescsoftware.weathersample.domain.interactor.city.api.model.Cities
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City
import com.francescsoftware.weathersample.domain.interactor.city.api.model.FavoriteCity
import com.francescsoftware.weathersample.domain.interactor.city.api.model.Metadata
import com.francescsoftware.weathersample.data.repository.city.api.model.City as RepoCity
import com.francescsoftware.weathersample.data.repository.city.api.model.Metadata as RepoMetadata
import com.francescsoftware.weathersample.data.repository.favorites.api.FavoriteCity as RepoFavoriteCity

internal fun CitySearchResponse.toCities(): Cities = Cities(
    metadata = metadata.toMetadata(),
    cities = cities.toCities(),
)

internal fun RepoMetadata.toMetadata(): Metadata = Metadata(
    offset = currentOffset,
    totalCount = totalCount,
)

internal fun List<RepoCity>.toCities(): List<City> = map { city -> city.toCity() }

internal fun RepoCity.toCity(): City = City(
    id = id,
    name = name,
    region = region,
    regionCode = regionCode,
    country = country,
    countryCode = countryCode,
    coordinates = coordinates,
)

internal fun RepoFavoriteCity.toFavoriteCity(): FavoriteCity = FavoriteCity(
    cityId = cityId,
    name = name,
    countryCode = countryCode,
)

internal fun FavoriteCity.toRepoFavoriteCity(): RepoFavoriteCity = RepoFavoriteCity(
    cityId = cityId,
    name = name,
    countryCode = countryCode,
)
