package com.francescsoftware.weathersample.data.repository.city.api

import app.cash.turbine.Turbine
import com.francescsoftware.weathersample.core.type.location.Coordinates
import com.francescsoftware.weathersample.data.repository.city.api.model.City
import com.francescsoftware.weathersample.data.repository.city.api.model.CitySearchResponse
import com.francescsoftware.weathersample.data.repository.city.api.model.Metadata

class FakeCityRepository : CityRepository {
    val cityList = Turbine<List<City>?>()

    override suspend fun getCitiesByPrefix(
        prefix: String,
        offset: Int,
        limit: Int,
    ): CitySearchResponse = this.getCities()

    override suspend fun getCitiesByLocation(
        location: Coordinates,
        offset: Int,
        limit: Int,
    ): CitySearchResponse = getCities()

    private suspend fun getCities(): CitySearchResponse {
        val cities = cityList.awaitItem()
        return cities?.let {
            CitySearchResponse(
                metadata = Metadata(
                    currentOffset = 0,
                    totalCount = it.size,
                ),
                cities = it,
            )
        } ?: throw CitiesException(message = "Failed to load cities")
    }
}
