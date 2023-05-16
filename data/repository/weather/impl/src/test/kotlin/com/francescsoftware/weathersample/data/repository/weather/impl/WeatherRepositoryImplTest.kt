package com.francescsoftware.weathersample.data.repository.weather.impl

import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.data.repository.weather.api.WeatherLocation
import com.francescsoftware.weathersample.data.repository.weather.impl.WeatherRepositoryImpl
import com.francescsoftware.weathersample.data.repository.weather.impl.WeatherService
import com.francescsoftware.weathersample.testing.fake.dispatcher.TestDispatcherProvider
import com.google.common.truth.Truth
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit

internal class WeatherRepositoryImplTest {

    private val json = Json { ignoreUnknownKeys = true }
    private val mediaType = "application/json".toMediaType()
    private val mockWebServer = MockWebServer()
    private val service = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(json.asConverterFactory(mediaType))
        .client(OkHttpClient.Builder().build())
        .build()
        .create(WeatherService::class.java)

    @AfterEach
    fun cleanUp() {
        mockWebServer.shutdown()
    }

    @Test
    fun `success network response returns today weather`() = runTest {
        val networkResponse = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setResponseCode(200)
            .setBody(TodayResponse)
        mockWebServer.enqueue(networkResponse)
        val repository = WeatherRepositoryImpl(
            weatherService = service,
            dispatcherProvider = TestDispatcherProvider(),
        )
        val location = WeatherLocation.City(
            name = "Vancouver",
            countryCode = "CA",
        )
        val response = repository.getTodayWeather(location = location)
        Truth.assertThat(response).isInstanceOf(Either.Success::class.java)
        val weather = (response as Either.Success).value
        Truth.assertThat(weather.current.tempCelsius).isEqualTo(14.0)
        Truth.assertThat(weather.current.tempFahrenheit).isEqualTo(57.2)
        Truth.assertThat(weather.current.windKph).isEqualTo(15.1)
        Truth.assertThat(weather.current.humidity).isEqualTo(82)
        Truth.assertThat(weather.current.pressureMb).isEqualTo(1015.0)
    }

    @Test
    fun `error network for today request returns failure`() = runTest {
        val networkResponse = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setResponseCode(500)
            .setBody("{}")
        mockWebServer.enqueue(networkResponse)
        val repository = WeatherRepositoryImpl(
            weatherService = service,
            dispatcherProvider = TestDispatcherProvider(),
        )
        val location = WeatherLocation.City(
            name = "Vancouver",
            countryCode = "CA",
        )
        val response = repository.getTodayWeather(location = location)
        Truth.assertThat(response).isInstanceOf(Either.Failure::class.java)
    }

    @Test
    fun `success network response returns forecast weather`() = runTest {
        val networkResponse = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setResponseCode(200)
            .setBody(ForecastResponse)
        mockWebServer.enqueue(networkResponse)
        val repository = WeatherRepositoryImpl(
            weatherService = service,
            dispatcherProvider = TestDispatcherProvider(),
        )
        val location = WeatherLocation.City(
            name = "Vancouver",
            countryCode = "CA",
        )
        val response = repository.getForecast(location = location)
        Truth.assertThat(response).isInstanceOf(Either.Success::class.java)
        val weather = (response as Either.Success).value
        // current
        Truth.assertThat(weather.current.tempCelsius).isEqualTo(14.3)
        Truth.assertThat(weather.current.tempFahrenheit).isEqualTo(57.7)
        Truth.assertThat(weather.current.windKph).isEqualTo(4.0)
        Truth.assertThat(weather.current.humidity).isEqualTo(62)
        Truth.assertThat(weather.current.pressureMb).isEqualTo(1015.0)

        // forecast
        Truth.assertThat(weather.forecast.forecastDay.size).isEqualTo(1)
        val hours = weather.forecast.forecastDay.first().hour
        Truth.assertThat(hours[0].tempCelsius).isEqualTo(9.6)
        Truth.assertThat(hours[0].feelsLikeCelsius).isEqualTo(9.1)
        Truth.assertThat(hours[0].windKph).isEqualTo(5.8)
        Truth.assertThat(hours[0].humidity).isEqualTo(78)
        Truth.assertThat(hours[0].visibilityKm).isEqualTo(10.0)
        Truth.assertThat(hours[0].precipitationMm).isEqualTo(0.0)
        Truth.assertThat(hours[0].condition.text).isEqualTo("Cloudy")
        Truth.assertThat(hours[1].tempCelsius).isEqualTo(9.1)
        Truth.assertThat(hours[1].feelsLikeCelsius).isEqualTo(8.3)
        Truth.assertThat(hours[1].windKph).isEqualTo(6.5)
        Truth.assertThat(hours[1].humidity).isEqualTo(80)
        Truth.assertThat(hours[1].visibilityKm).isEqualTo(9.0)
        Truth.assertThat(hours[1].precipitationMm).isEqualTo(5.0)
        Truth.assertThat(hours[1].condition.text).isEqualTo("Partly cloudy")
        Truth.assertThat(hours[2].tempCelsius).isEqualTo(9.0)
        Truth.assertThat(hours[2].feelsLikeCelsius).isEqualTo(8.7)
        Truth.assertThat(hours[2].windKph).isEqualTo(5.0)
        Truth.assertThat(hours[2].humidity).isEqualTo(79)
        Truth.assertThat(hours[2].visibilityKm).isEqualTo(10.0)
        Truth.assertThat(hours[2].precipitationMm).isEqualTo(7.5)
        Truth.assertThat(hours[2].condition.text).isEqualTo("Clear")
        val astro = weather.forecast.forecastDay.first().astro
        Truth.assertThat(astro.sunrise).isEqualTo("05:40 AM")
        Truth.assertThat(astro.sunset).isEqualTo("08:39 PM")
    }

    @Test
    fun `error network for forecast request returns failure`() = runTest {
        val networkResponse = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setResponseCode(500)
            .setBody("{}")
        mockWebServer.enqueue(networkResponse)
        val repository = WeatherRepositoryImpl(
            weatherService = service,
            dispatcherProvider = TestDispatcherProvider(),
        )
        val location = WeatherLocation.City(
            name = "Vancouver",
            countryCode = "CA",
        )
        val response = repository.getForecast(location = location)
        Truth.assertThat(response).isInstanceOf(Either.Failure::class.java)
    }
}
