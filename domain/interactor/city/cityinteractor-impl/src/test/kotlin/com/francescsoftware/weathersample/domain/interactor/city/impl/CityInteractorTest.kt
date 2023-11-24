package com.francescsoftware.weathersample.domain.interactor.city.impl

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import com.francescsoftware.weathersample.core.dispatcher.TestDispatcherProvider
import com.francescsoftware.weathersample.core.type.either.isFailure
import com.francescsoftware.weathersample.core.type.either.isSuccess
import com.francescsoftware.weathersample.core.type.either.throwableOrNull
import com.francescsoftware.weathersample.core.type.either.valueOrNull
import com.francescsoftware.weathersample.core.type.location.Coordinates
import com.francescsoftware.weathersample.data.repository.city.api.FakeCityRepository
import com.francescsoftware.weathersample.data.repository.city.api.model.City
import com.francescsoftware.weathersample.domain.interactor.city.api.CitiesException
import com.francescsoftware.weathersample.domain.interactor.city.api.GetCitiesInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City as DomainCity

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
        City(
            id = 1,
            city = CityName,
            name = CityName,
            region = CityRegion,
            regionCode = CityRegionCode,
            country = CityCountry,
            countryCode = CityCountryCode,
            coordinates = Coordinates(
                latitude = CityLatitude,
                longitude = CityLongitude,
            ),
        ),
    )

    private val successCity =
        DomainCity(
            id = 1,
            name = CityName,
            region = CityRegion,
            regionCode = CityRegionCode,
            country = CityCountry,
            countryCode = CityCountryCode,
            coordinates = Coordinates(
                latitude = CityLatitude,
                longitude = CityLongitude,
            ),
        )

    @Test
    fun `interactor maps network data to interactor city data`() = runTest {
        // pre
        val repository = FakeCityRepository().apply { cityList.add(citiesResponse) }
        val interactor = GetCitiesInteractorImpl(repository, TestDispatcherProvider())

        // when we execute the interactor query
        val query = CityName
        val response = interactor(GetCitiesInteractor.Params(query))

        // the response has been converted to the interactor type
        assertThat(response.isSuccess).isTrue()
        assertThat(response.valueOrNull()?.cities).isEqualTo(listOf(successCity))
    }

    @Test
    fun `interactor maps network error to interactor error`() = runTest {
        val repository = FakeCityRepository().apply { cityList.add(null) }
        val interactor = GetCitiesInteractorImpl(repository, TestDispatcherProvider())

        // when we execute the interactor query
        val query = CityName
        val response = interactor(GetCitiesInteractor.Params(query))

        // the response has been converted to the interactor type
        assertThat(response.isFailure).isTrue()
        assertThat(response.throwableOrNull()).isNotNull().isInstanceOf<CitiesException>()
    }
}
