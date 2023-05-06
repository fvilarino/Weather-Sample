package com.francescsoftware.weathersample.cityrepository.impl

import com.francescsoftware.weathersample.cityrepository.api.model.City
import com.francescsoftware.weathersample.cityrepository.api.model.Coordinates
import com.francescsoftware.weathersample.cityrepository.impl.model.CityModel
import com.francescsoftware.weathersample.cityrepository.impl.model.CitySearchResponseModel
import com.francescsoftware.weathersample.cityrepository.impl.model.MetadataModel
import com.francescsoftware.weathersample.dispatcher.TestDispatcherProvider
import com.francescsoftware.weathersample.type.Either
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import retrofit2.Response

private const val VancouverName = "Vancouver"
private const val VancouverRegion = "British Columbia"
private const val VancouverRegionCode = "BC"
private const val VancouverCountry = "Canada"
private const val VancouverCountryCode = "CA"
private const val VancouverLatitude = 49.24
private const val VancouverLongitude = -123.11

private const val BarcelonaName = "Barcelona"
private const val BarcelonaRegion = "Catalunya"
private const val BarcelonaRegionCode = "CA"
private const val BarcelonaCountry = "Spain"
private const val BarcelonaCountryCode = "ES"
private const val BarcelonaLatitude = 41.39
private const val BarcelonaLongitude = 2.17

internal class CityRepositoryImplTest {

    private val networkCities = listOf(
        CityModel(
            id = 1,
            city = VancouverName,
            name = VancouverName,
            region = VancouverRegion,
            regionCode = VancouverRegionCode,
            country = VancouverCountry,
            countryCode = VancouverCountryCode,
            latitude = VancouverLatitude,
            longitude = VancouverLongitude,
        ),
        CityModel(
            id = 2,
            city = BarcelonaName,
            name = BarcelonaName,
            region = BarcelonaRegion,
            regionCode = BarcelonaRegionCode,
            country = BarcelonaCountry,
            countryCode = BarcelonaCountryCode,
            latitude = BarcelonaLatitude,
            longitude = BarcelonaLongitude,
        ),
    )

    private val expectedCities = listOf(
        City(
            id = 1,
            city = VancouverName,
            name = VancouverName,
            region = VancouverRegion,
            regionCode = VancouverRegionCode,
            country = VancouverCountry,
            countryCode = VancouverCountryCode,
            coordinates = Coordinates(
                latitude = VancouverLatitude,
                longitude = VancouverLongitude,
            ),
        ),
        City(
            id = 2,
            city = BarcelonaName,
            name = BarcelonaName,
            region = BarcelonaRegion,
            regionCode = BarcelonaRegionCode,
            country = BarcelonaCountry,
            countryCode = BarcelonaCountryCode,
            coordinates = Coordinates(
                latitude = BarcelonaLatitude,
                longitude = BarcelonaLongitude,
            ),
        ),
    )

    private inner class FakeCityService : CityService {
        var cities: List<CityModel> = emptyList()
        var networkError: Boolean = false

        override suspend fun getCities(namePrefix: String?, limit: Int?): Response<CitySearchResponseModel> {
            return if (networkError) {
                Response.error(
                    404,
                    "{\"key\":[\"URL does not exist\"]}"
                        .toResponseBody("application/json".toMediaTypeOrNull()),
                )
            } else {
                Response.success(
                    CitySearchResponseModel(
                        metadata = MetadataModel(
                            currentOffset = 0,
                            totalCount = 0,
                        ),
                        data = cities,
                    )
                )
            }
        }
    }

    @Test
    fun `success network response returns cities`() = runTest {
        val service = FakeCityService().apply { cities = networkCities }
        val repository = CityRepositoryImpl(cityService = service, dispatcherProvider = TestDispatcherProvider())
        val response = repository.getCities(prefix = "", limit = 10)
        Assertions.assertTrue(response is Either.Success)
        val cityList = (response as Either.Success).value.cities
        Assertions.assertEquals(cityList.size, networkCities.size)
        cityList.forEachIndexed { index, city ->
            Assertions.assertEquals(city, expectedCities[index])
        }
    }

    @Test
    fun `invalid data returns error`() = runTest {
        val invalidCities = networkCities + CityModel(
            name = "Tokyo",
            region = null,
            regionCode = null,
            country = "Japan",
            countryCode = "JP",
            latitude = 0.0,
            longitude = 0.0,
        )

        val service = FakeCityService().apply { cities = invalidCities }
        val repository = CityRepositoryImpl(cityService = service, dispatcherProvider = TestDispatcherProvider())
        val response = repository.getCities(prefix = "", limit = 10)
        Assertions.assertTrue(response is Either.Failure)
    }

    @Test
    fun `network errors returns error`() = runTest {
        val service = FakeCityService().apply { networkError = true }
        val repository = CityRepositoryImpl(cityService = service, dispatcherProvider = TestDispatcherProvider())
        val response = repository.getCities(prefix = "", limit = 10)
        Assertions.assertTrue(response is Either.Failure)
    }
}
