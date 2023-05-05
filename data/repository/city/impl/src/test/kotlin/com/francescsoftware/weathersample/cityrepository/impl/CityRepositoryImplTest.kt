package com.francescsoftware.weathersample.cityrepository.impl

import com.francescsoftware.weathersample.cityrepository.api.model.CityItem
import com.francescsoftware.weathersample.cityrepository.api.model.CitySearchResponse
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
        CityItem(
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
        CityItem(
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

    private inner class FakeCityService : CityService {
        var cities: List<CityItem> = emptyList()
        var networkError: Boolean = false

        override suspend fun getCities(namePrefix: String?, limit: Int?): Response<CitySearchResponse> {
            return if (networkError) {
                Response.error(
                    404,
                    "{\"key\":[\"URL does not exist\"]}"
                        .toResponseBody("application/json".toMediaTypeOrNull()),
                )
            } else {
                Response.success(
                    CitySearchResponse(
                        data = cities
                    )
                )
            }
        }
    }

    @Test
    fun `success network response returns cities`() = runTest {
        val service = FakeCityService().apply { cities = networkCities }
        val repository = CityRepositoryImpl(cityService = service)
        val response = repository.getCities(prefix = "", limit = 10)
        Assertions.assertTrue(response is Either.Success)
        val cityList = (response as Either.Success).value.data!!
        Assertions.assertEquals(cityList.size, networkCities.size)
        networkCities.forEachIndexed { index, networkCity ->
            Assertions.assertEquals(networkCity, cityList[index])
        }
    }

    @Test
    fun `network errors returns error`() = runTest {
        val service = FakeCityService().apply { networkError = true }
        val repository = CityRepositoryImpl(cityService = service)
        val response = repository.getCities(prefix = "", limit = 10)
        Assertions.assertTrue(response is Either.Failure)
    }
}
