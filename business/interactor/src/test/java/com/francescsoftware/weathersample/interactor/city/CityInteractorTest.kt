package com.francescsoftware.weathersample.interactor.city

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.francescsoftware.weathersample.repository.city.CityRepository
import com.francescsoftware.weathersample.repository.city.model.CityItem
import com.francescsoftware.weathersample.repository.city.model.CitySearchResponse
import com.francescsoftware.weathersample.testing.MainCoroutineRule
import com.francescsoftware.weathersample.testing.runBlockingTest
import com.francescsoftware.weathersample.type.Result
import com.francescsoftware.weathersample.type.getOrNull
import com.francescsoftware.weathersample.type.isFailure
import com.francescsoftware.weathersample.type.isSuccess
import com.francescsoftware.weathersample.type.throwableOrNull
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

private const val CityName = "Vancouver"
private const val CityRegion = "British Columbia"
private const val CityRegionCode = "BC"
private const val CityCountry = "Canada"
private const val CityCountryCode = "CA"
private const val CityLatitude = 49.24
private const val CityLongitude = -123.11

@ExperimentalCoroutinesApi
class CityInteractorTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var cityRepository: CityRepository

    private val citiesResponse = CitySearchResponse(
        data = listOf(
            CityItem(
                id = 1,
                city = CityName,
                name = CityName,
                region = CityRegion,
                regionCode = CityRegionCode,
                country = CityCountry,
                countryCode = CityCountryCode,
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

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        coEvery { cityRepository.getCities(any(), any()) } returns Result.Success(
            citiesResponse
        )
    }

    @Test
    fun `interactor calls repository with incoming arguments`() {
        mainCoroutineRule.runBlockingTest {
            // pre
            val interactor = GetCitiesInteractorImpl(cityRepository)

            // when we execute the interactor query
            val query = CityName
            interactor.execute(query)

            // we call the repository once with the same argument
            coVerify(exactly = 1) {
                cityRepository.getCities(
                    withArg { arg ->
                        assertEquals(arg, query)
                    }
                )
            }
        }
    }

    @Test
    fun `interactor maps network data to interactor city data`() {
        mainCoroutineRule.runBlockingTest {
            // pre
            val interactor = GetCitiesInteractorImpl(cityRepository)

            // when we execute the interactor query
            val query = CityName
            val response = interactor.execute(query)

            // the response has been converted to the interactor type
            assertTrue(response.isSuccess)
            assertEquals(response.getOrNull(), listOf(successCity))
        }
    }

    @Test
    fun `interactor maps network error to interactor error`() {
        mainCoroutineRule.runBlockingTest {
            // pre
            val interactor = GetCitiesInteractorImpl(cityRepository)
            coEvery {
                cityRepository.getCities(
                    any(),
                    any()
                )
            } returns Result.Failure(IOException("Failed to load cities"))

            // when we execute the interactor query
            val query = CityName
            val response = interactor.execute(query)

            // the response has been converted to the interactor type
            assertTrue(response.isFailure)
            assertTrue(response.throwableOrNull() is CitiesException)
        }
    }
}
