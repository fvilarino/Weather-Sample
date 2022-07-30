package com.francescsoftware.weathersample.interactor.weather.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.francescsoftware.weathersample.interactor.weather.api.TodayClouds
import com.francescsoftware.weathersample.interactor.weather.api.TodayMain
import com.francescsoftware.weathersample.interactor.weather.api.TodayWeather
import com.francescsoftware.weathersample.interactor.weather.api.TodayWind
import com.francescsoftware.weathersample.interactor.weather.api.WeatherException
import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.testing.MainCoroutineRule
import com.francescsoftware.weathersample.type.Result
import com.francescsoftware.weathersample.type.getOrNull
import com.francescsoftware.weathersample.type.isFailure
import com.francescsoftware.weathersample.type.isSuccess
import com.francescsoftware.weathersample.type.throwableOrNull
import com.francescsoftware.weathersample.weatherrepository.api.WeatherRepository
import com.francescsoftware.weathersample.weatherrepository.api.model.Condition
import com.francescsoftware.weathersample.weatherrepository.api.model.Current
import com.francescsoftware.weathersample.weatherrepository.api.model.today.TodayWeatherResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import kotlin.math.roundToInt
import com.francescsoftware.weathersample.weatherrepository.api.WeatherLocation as RepositoryLocation

private const val cityName = "Vancouver"
private const val countryCode = "CA"
private const val CityLatitude = 49.24
private const val CityLongitude = -123.11

private const val conditionCode = 1048
private const val currentTemperature = 23.0
private const val precipitation = 14
private const val feelsLikeTemperature = 21.4
private const val humidityPercent = 54
private const val pressureMb = 1024.0
private const val visibilityKilometers = 10
private const val clouds = 32
private const val weatherDescription = "sunny"
private const val windSpeed = 4.5
private const val gustSpeed = 8.7
private const val windDirection = "N"
private const val uvIndex = 7

@ExperimentalCoroutinesApi
class TodayWeatherInteractorTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var weatherRepository: WeatherRepository

    private val todayWeatherResponse = TodayWeatherResponse(
        current = Current(
            condition = Condition(
                code = conditionCode,
                icon = "",
                text = weatherDescription,
            ),
            tempC = currentTemperature,
            tempF = currentTemperature * 1.8 + 32.0,
            feelslikeC = feelsLikeTemperature,
            feelslikeF = feelsLikeTemperature * 1.8 + 32.0,
            uv = uvIndex.toDouble(),
            precipMm = precipitation.toDouble(),
            humidity = humidityPercent,
            pressureMb = pressureMb,
            windDir = "N",
            windKph = windSpeed,
            gustKph = gustSpeed,
            cloud = clouds,
            visKm = visibilityKilometers.toDouble()
        )
    )

    private val successfulTodayWeather = TodayWeather(
        main = TodayMain(
            description = weatherDescription,
            temp = currentTemperature,
            feelsLike = feelsLikeTemperature,
            humidity = humidityPercent,
            pressure = pressureMb.roundToInt(),
            precipitation = precipitation,
            code = conditionCode,
            uvIndex = uvIndex,
        ),
        wind = TodayWind(
            direction = windDirection,
            speed = windSpeed,
            gust = gustSpeed,
        ),
        clouds = TodayClouds(
            all = clouds,
        ),
        visibility = visibilityKilometers,
    )

    private val incomingCity: WeatherLocation = WeatherLocation.City(
        name = cityName,
        countryCode = countryCode,
    )

    private val incomingCoordinates: WeatherLocation = WeatherLocation.Coordinates(
        latitude = CityLatitude,
        longitude = CityLongitude,
    )

    private val queryCity: RepositoryLocation = RepositoryLocation.City(
        name = cityName,
        countryCode = countryCode,
    )

    private val queryCoordinates: RepositoryLocation = RepositoryLocation.Coordinates(
        latitude = CityLatitude,
        longitude = CityLongitude,
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        coEvery { weatherRepository.getTodayWeather(any()) } returns Result.Success(
            todayWeatherResponse
        )
    }

    @Test
    fun `interactor calls repository with incoming city arguments`() = runTest {
        // pre
        val interactor = GetTodayWeatherInteractorImpl(weatherRepository)

        // when we execute the interactor query
        interactor.execute(incomingCity)

        // we call the repository once with the same argument
        coVerify(exactly = 1) {
            weatherRepository.getTodayWeather(
                withArg { arg ->
                    assertEquals(arg, queryCity)
                }
            )
        }
    }

    @Test
    fun `interactor calls repository with incoming coordinate arguments`() = runTest {
        // pre
        val interactor = GetTodayWeatherInteractorImpl(weatherRepository)

        // when we execute the interactor query
        interactor.execute(incomingCoordinates)

        // we call the repository once with the same argument
        coVerify(exactly = 1) {
            weatherRepository.getTodayWeather(
                withArg { arg ->
                    assertEquals(arg, queryCoordinates)
                }
            )
        }
    }

    @Test
    fun `interactor maps network data to interactor today weather`() = runTest {
        // pre
        val interactor = GetTodayWeatherInteractorImpl(weatherRepository)

        // when we execute the interactor query
        val response = interactor.execute(incomingCity)

        // the response has been converted to the interactor type
        Assert.assertTrue(response.isSuccess)
        assertEquals(response.getOrNull(), successfulTodayWeather)
    }

    @Test
    fun `interactor maps network data to interactor forecast weather`() = runTest {
        // pre
        val interactor = GetTodayWeatherInteractorImpl(weatherRepository)

        // when we execute the interactor query
        val response = interactor.execute(incomingCity)

        // the response has been converted to the interactor type
        Assert.assertTrue(response.isSuccess)
        assertEquals(response.getOrNull(), successfulTodayWeather)
    }

    @Test
    fun `interactor maps network error to interactor error`() = runTest {
        // pre
        val interactor = GetTodayWeatherInteractorImpl(weatherRepository)
        coEvery {
            weatherRepository.getTodayWeather(any())
        } returns Result.Failure(IOException("Failed to load today weather"))

        // when we execute the interactor query
        val response = interactor.execute(incomingCity)

        // the response has been converted to the interactor type
        Assert.assertTrue(response.isFailure)
        Assert.assertTrue(response.throwableOrNull() is WeatherException)
    }
}
