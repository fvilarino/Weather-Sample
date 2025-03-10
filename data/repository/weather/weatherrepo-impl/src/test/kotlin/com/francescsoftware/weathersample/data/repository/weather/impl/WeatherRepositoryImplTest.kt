package com.francescsoftware.weathersample.data.repository.weather.impl

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import com.francescsoftware.weathersample.core.dispatcher.TestDispatcherProvider
import com.francescsoftware.weathersample.core.type.either.Either
import com.francescsoftware.weathersample.data.repository.weather.api.WeatherLocation
import com.francescsoftware.weathersample.data.repository.weather.api.model.today.TodayWeatherResponse
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

internal class WeatherRepositoryImplTest {

    private val json = Json { ignoreUnknownKeys = true }
    private val mockWebServer = MockWebServer()
    private val service = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(
            json.asConverterFactory(
                "application/json; charset=UTF8".toMediaType(),
            ),
        )
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
        assertThat(response).isNotNull().isInstanceOf<Either.Success<TodayWeatherResponse>>()
        val weather = (response as Either.Success).value
        assertThat(weather.current.tempCelsius).isEqualTo(14.0)
        assertThat(weather.current.tempFahrenheit).isEqualTo(57.2)
        assertThat(weather.current.windKph).isEqualTo(15.1)
        assertThat(weather.current.humidity).isEqualTo(82)
        assertThat(weather.current.pressureMb).isEqualTo(1015.0)
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
        assertThat(response).isInstanceOf<Either.Failure>()
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
        assertThat(response).isNotNull().isInstanceOf<Either.Success<TodayWeatherResponse>>()
        val weather = (response as Either.Success).value
        // current
        assertThat(weather.current.tempCelsius).isEqualTo(14.3)
        assertThat(weather.current.tempFahrenheit).isEqualTo(57.7)
        assertThat(weather.current.windKph).isEqualTo(4.0)
        assertThat(weather.current.humidity).isEqualTo(62)
        assertThat(weather.current.pressureMb).isEqualTo(1015.0)

        // forecast
        assertThat(weather.forecast.forecastDay.size).isEqualTo(1)
        val hours = weather.forecast.forecastDay.first().hour
        assertThat(hours[0].tempCelsius).isEqualTo(9.6)
        assertThat(hours[0].feelsLikeCelsius).isEqualTo(9.1)
        assertThat(hours[0].windKph).isEqualTo(5.8)
        assertThat(hours[0].humidity).isEqualTo(78)
        assertThat(hours[0].visibilityKm).isEqualTo(10.0)
        assertThat(hours[0].precipitationMm).isEqualTo(0.0)
        assertThat(hours[0].condition.text).isEqualTo("Cloudy")
        assertThat(hours[1].tempCelsius).isEqualTo(9.1)
        assertThat(hours[1].feelsLikeCelsius).isEqualTo(8.3)
        assertThat(hours[1].windKph).isEqualTo(6.5)
        assertThat(hours[1].humidity).isEqualTo(80)
        assertThat(hours[1].visibilityKm).isEqualTo(9.0)
        assertThat(hours[1].precipitationMm).isEqualTo(5.0)
        assertThat(hours[1].condition.text).isEqualTo("Partly cloudy")
        assertThat(hours[2].tempCelsius).isEqualTo(9.0)
        assertThat(hours[2].feelsLikeCelsius).isEqualTo(8.7)
        assertThat(hours[2].windKph).isEqualTo(5.0)
        assertThat(hours[2].humidity).isEqualTo(79)
        assertThat(hours[2].visibilityKm).isEqualTo(10.0)
        assertThat(hours[2].precipitationMm).isEqualTo(7.5)
        assertThat(hours[2].condition.text).isEqualTo("Clear")
        val astro = weather.forecast.forecastDay.first().astro
        assertThat(astro.sunrise).isEqualTo("05:40 AM")
        assertThat(astro.sunset).isEqualTo("08:39 PM")
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
        assertThat(response).isInstanceOf<Either.Failure>()
    }
}
