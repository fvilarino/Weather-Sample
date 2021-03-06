package com.francescsoftware.weathersample.interactor.city

import com.francescsoftware.weathersample.repository.city.CityRepository
import com.francescsoftware.weathersample.repository.city.model.CityItem
import com.francescsoftware.weathersample.repository.city.model.CitySearchResponse
import com.francescsoftware.weathersample.type.Result
import com.francescsoftware.weathersample.type.fold
import javax.inject.Inject

class GetCitiesInteractorImpl @Inject constructor(
    private val cityRepository: CityRepository
) : GetCitiesInteractor {

    override suspend fun execute(prefix: String, limit: Int): Result<List<City>> {
        val citiesResponse: Result<CitySearchResponse> = cityRepository.getCities(prefix, limit)
        return citiesResponse.fold(
            onSuccess = { response ->
                val data = response.data
                if (data != null) {
                    val cities = data
                        .filter { city -> city.isValid }
                        .map { city -> city.toCity() }
                    Result.Success(cities)
                } else {
                    Result.Success(emptyList())
                }
            },
            onFailure = { response ->
                val cause = response.cause
                Result.Failure(
                    if (cause != null) {
                        CitiesException("Error fetching cities", cause)
                    } else {
                        CitiesException("Error fetching cities")
                    }
                )
            }
        )
    }

    private val CityItem.isValid: Boolean
        get() = id != null &&
            name != null &&
            region != null &&
            regionCode != null &&
            country != null &&
            countryCode != null &&
            latitude != null &&
            longitude != null

    private fun CityItem.toCity() = City(
        id = id ?: 0,
        name = name.orEmpty(),
        region = region.orEmpty(),
        regionCode = regionCode.orEmpty(),
        country = country.orEmpty(),
        countryCode = countryCode.orEmpty(),
        coordinates = Coordinates(
            latitude = latitude ?: 0.0,
            longitude = longitude ?: 0.0,
        )
    )
}