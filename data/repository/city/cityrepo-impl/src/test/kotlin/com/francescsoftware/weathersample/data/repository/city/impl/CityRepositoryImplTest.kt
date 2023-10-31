package com.francescsoftware.weathersample.data.repository.city.impl

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.francescsoftware.weathersample.core.dispatcher.TestDispatcherProvider
import com.francescsoftware.weathersample.data.repository.city.api.CitiesException
import com.francescsoftware.weathersample.data.repository.city.api.model.City
import com.francescsoftware.weathersample.data.repository.city.api.model.Coordinates
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import retrofit2.Retrofit

private val ExpectedCities = listOf(
    City(
        id = 3323992,
        city = "Milwich",
        name = "Milwich",
        region = "Staffordshire",
        regionCode = "STS",
        country = "United Kingdom",
        countryCode = "GB",
        coordinates = Coordinates(
            latitude = 52.8879,
            longitude = -2.04314,
        ),
    ),
    City(
        id = 3415353,
        city = "Milwino",
        name = "Milwino",
        region = "Pomeranian Voivodeship",
        regionCode = "22",
        country = "Poland",
        countryCode = "PL",
        coordinates = Coordinates(
            latitude = 54.51944,
            longitude = 18.13167,
        ),
    ),
    City(
        id = 3371102,
        city = "Milwaukee",
        name = "Milwaukee",
        region = "North Carolina",
        regionCode = "NC",
        country = "United States of America",
        countryCode = "US",
        coordinates = Coordinates(
            latitude = 36.4056,
            longitude = -77.2322,
        ),
    ),
)

internal class CityRepositoryImplTest {

    private val json = Json { ignoreUnknownKeys = true }
    private val mediaType = "application/json".toMediaType()
    private val mockWebServer = MockWebServer()
    private val service = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(json.asConverterFactory(mediaType))
        .client(OkHttpClient.Builder().build())
        .build()
        .create(CityService::class.java)

    @AfterEach
    fun cleanUp() {
        mockWebServer.shutdown()
    }

    @Test
    fun `success network response returns cities`() = runTest {
        val networkResponse = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setResponseCode(200)
            .setBody(CitiesResponse)
        mockWebServer.enqueue(networkResponse)
        val repository = CityRepositoryImpl(
            cityService = service,
            dispatcherProvider = TestDispatcherProvider(),
        )
        val response = repository.getCities(prefix = "", limit = 10)
        val cityList = response.cities
        assertThat(cityList.size).isEqualTo(ExpectedCities.size)
        cityList.forEachIndexed { index, city ->
            assertThat(city).isEqualTo(ExpectedCities[index])
        }
    }

    @Test
    fun `invalid data returns error`() = runTest {
        val networkResponse = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setResponseCode(200)
            .setBody(InvalidCitiesResponse)
        mockWebServer.enqueue(networkResponse)
        val repository = CityRepositoryImpl(
            cityService = service,
            dispatcherProvider = TestDispatcherProvider(),
        )
        assertThrows<CitiesException> { repository.getCities(prefix = "", limit = 10) }
    }

    @Test
    fun `400 network error returns error`() = runTest {
        val networkResponse = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setResponseCode(404)
            .setBody("{}")
        mockWebServer.enqueue(networkResponse)
        val repository = CityRepositoryImpl(
            cityService = service,
            dispatcherProvider = TestDispatcherProvider(),
        )
        assertThrows<CitiesException> { repository.getCities(prefix = "", limit = 10) }
    }

    @Test
    fun `500 network error returns error`() = runTest {
        val networkResponse = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setResponseCode(500)
            .setBody("{}")
        mockWebServer.enqueue(networkResponse)
        val repository = CityRepositoryImpl(
            cityService = service,
            dispatcherProvider = TestDispatcherProvider(),
        )
        assertThrows<CitiesException> { repository.getCities(prefix = "", limit = 10) }
    }
}
