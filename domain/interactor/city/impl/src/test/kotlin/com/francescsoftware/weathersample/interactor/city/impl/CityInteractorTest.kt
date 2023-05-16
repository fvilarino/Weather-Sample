package com.francescsoftware.weathersample.interactor.city.impl

import com.francescsoftware.weathersample.cityrepository.api.CityRepository
import com.francescsoftware.weathersample.cityrepository.api.model.CitySearchResponse
import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.core.type.either.isFailure
import com.francescsoftware.weathersample.core.type.either.isSuccess
import com.francescsoftware.weathersample.core.type.either.throwableOrNull
import com.francescsoftware.weathersample.core.type.either.valueOrNull
import com.francescsoftware.weathersample.interactor.city.api.CitiesException
import com.francescsoftware.weathersample.interactor.city.api.model.City
import com.francescsoftware.weathersample.interactor.city.api.model.Coordinates
import com.francescsoftware.weathersample.testing.fake.dispatcher.TestDispatcherProvider
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import com.francescsoftware.weathersample.cityrepository.api.CitiesException as RepoException
import com.francescsoftware.weathersample.cityrepository.api.model.City as RepoCity
import com.francescsoftware.weathersample.cityrepository.api.model.Coordinates as RepoCoordinates
import com.francescsoftware.weathersample.cityrepository.api.model.Metadata as RepoMetadata

private const val CityName = "Vancouver"
private const val CityRegion = "British Columbia"
private const val CityRegionCode = "BC"
private const val CityCountry = "Canada"
private const val CityCountryCode = "CA"
private const val CityLatitude = 49.24
private const val CityLongitude = -123.11

@ExperimentalCoroutinesApi
class CityInteractorTest {

    private val citiesResponse = listOf(
        RepoCity(
            id = 1,
            city = CityName,
            name = CityName,
            region = CityRegion,
            regionCode = CityRegionCode,
            country = CityCountry,
            countryCode = CityCountryCode,
            coordinates = RepoCoordinates(
                latitude = CityLatitude,
                longitude = CityLongitude,
            )
        )
    )

    private val successCity = City(
        id = 1,
        name = CityName,
        region = CityRegion,
        regionCode = CityRegionCode,
        country = CityCountry,
        countryCode = CityCountryCode,
        coordinates = Coordinates(latitude = CityLatitude, longitude = CityLongitude)
    )

    private class FakeCityRepository : CityRepository {
        var cities: List<RepoCity> = emptyList()
        var isNetworkError: Boolean = false

        override suspend fun getCities(prefix: String, limit: Int): Either<CitySearchResponse> {
            return if (isNetworkError) {
                Either.Failure(RepoException(message = "Failed to load cities"))
            } else {
                Either.Success(
                    CitySearchResponse(
                        metadata = RepoMetadata(0, 0),
                        cities = cities,
                    )
                )
            }
        }
    }

    @Test
    fun `interactor maps network data to interactor city data`() = runTest {
        // pre
        val repository = FakeCityRepository().apply { cities = citiesResponse }
        val interactor = GetCitiesInteractorImpl(repository, TestDispatcherProvider())

        // when we execute the interactor query
        val query = CityName
        val response = interactor(query)

        // the response has been converted to the interactor type
        Truth.assertThat(response.isSuccess).isTrue()
        Truth.assertThat(response.valueOrNull()?.cities).isEqualTo(listOf(successCity))
    }

    @Test
    fun `interactor maps network error to interactor error`() = runTest {
        val repository = FakeCityRepository().apply { isNetworkError = true }
        val interactor = GetCitiesInteractorImpl(repository, TestDispatcherProvider())

        // when we execute the interactor query
        val query = CityName
        val response = interactor(query)

        // the response has been converted to the interactor type
        Truth.assertThat(response.isFailure).isTrue()
        Truth.assertThat(response.throwableOrNull()).isInstanceOf(CitiesException::class.java)
    }
}
