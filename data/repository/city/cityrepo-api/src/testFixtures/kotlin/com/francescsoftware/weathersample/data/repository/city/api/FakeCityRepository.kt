package com.francescsoftware.weathersample.data.repository.city.api

import com.francescsoftware.weathersample.data.repository.city.api.model.City
import com.francescsoftware.weathersample.data.repository.city.api.model.CitySearchResponse
import com.francescsoftware.weathersample.data.repository.city.api.model.Metadata

class FakeCityRepository : CityRepository {
    var cities: List<City> = emptyList()
    var isNetworkError: Boolean = false

    override suspend fun getCities(prefix: String, offset: Int, limit: Int): CitySearchResponse =
        if (isNetworkError) {
            throw CitiesException(message = "Failed to load cities")
        } else {
            CitySearchResponse(
                metadata = Metadata(0, 0),
                cities = cities,
            )
        }
}
