package com.francescsoftware.weathersample.interactor.weather.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.francescsoftware.weathersample.interactor.weather.api.TodayClouds
import com.francescsoftware.weathersample.interactor.weather.api.TodayMain
import com.francescsoftware.weathersample.interactor.weather.api.TodayWeather
import com.francescsoftware.weathersample.interactor.weather.api.TodayWeatherItem
import com.francescsoftware.weathersample.interactor.weather.api.TodayWind
import com.francescsoftware.weathersample.interactor.weather.api.WeatherException
import com.francescsoftware.weathersample.testing.MainCoroutineRule
import com.francescsoftware.weathersample.testing.runBlockingTest
import com.francescsoftware.weathersample.type.Result
import com.francescsoftware.weathersample.type.getOrNull
import com.francescsoftware.weathersample.type.isFailure
import com.francescsoftware.weathersample.type.isSuccess
import com.francescsoftware.weathersample.type.throwableOrNull
import com.francescsoftware.weathersample.weatherrepository.api.WeatherRepository
import com.francescsoftware.weathersample.weatherrepository.api.model.Clouds
import com.francescsoftware.weathersample.weatherrepository.api.model.Main
import com.francescsoftware.weathersample.weatherrepository.api.model.WeatherItem
import com.francescsoftware.weathersample.weatherrepository.api.model.Wind
import com.francescsoftware.weathersample.weatherrepository.api.model.today.TodayWeatherResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import com.francescsoftware.weathersample.weatherrepository.api.WeatherLocation as RepositoryLocation

private const val cityName = "Vancouver"
private const val countryCode = "CA"
private const val CityLatitude = 49.24
private const val CityLongitude = -123.11

private const val currentTemperature = 23.0
private const val minTemperature = 11.5
private const val maxTemperature = 26.3
private const val feelsLikeTemperature = 21.4
private const val humidityPercent = 54
private const val pressureMb = 1024
private const val visibilityMeters = 10000
private const val clouds = 32
private const val iconId = "d01"
private const val weatherDescription = "sunny"
private const val weatherMain = "sunny"
private const val windSpeed = 4.5
private const val windDirection = 270

@ExperimentalCoroutinesApi
class TodayWeatherInteractorTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var weatherRepository: WeatherRepository

    private val todayWeatherResponse = TodayWeatherResponse(
        main = Main(
            temp = currentTemperature,
            tempMin = minTemperature,
            tempMax = maxTemperature,
            feelsLike = feelsLikeTemperature,
            humidity = humidityPercent,
            pressure = pressureMb,
        ),
        clouds = Clouds(
            all = clouds,
        ),
        weather = listOf(
            WeatherItem(
                icon = iconId,
                description = weatherDescription,
                main = weatherMain,
            )
        ),
        wind = Wind(
            deg = windDirection,
            speed = windSpeed,
        ),
        visibility = visibilityMeters,
    )

    private val successfulTodayWeather = TodayWeather(
        weather = TodayWeatherItem(
            icon = iconId,
            description = weatherDescription,
            main = weatherMain,
        ),
        main = TodayMain(
            temp = currentTemperature,
            tempMin = minTemperature,
            tempMax = maxTemperature,
            feelsLike = feelsLikeTemperature,
            humidity = humidityPercent,
            pressure = pressureMb,
        ),
        wind = TodayWind(
            deg = windDirection,
            speed = windSpeed,
            gust = 0.0,
        ),
        clouds = TodayClouds(
            all = clouds,
        ),
        visibility = visibilityMeters,
    )

    private val incomingCity = RepositoryLocation.City(
        name = cityName,
        countryCode = countryCode,
    )

    private val incomingCoordinates = RepositoryLocation.Coordinates(
        latitude = CityLatitude,
        longitude = CityLongitude,
    )

    private val queryCity = RepositoryLocation.City(
        name = cityName,
        countryCode = countryCode,
    )

    private val queryCoordinates = RepositoryLocation.Coordinates(
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
    fun `interactor calls repository with incoming city arguments`() {
        mainCoroutineRule.runBlockingTest {
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
    }

    @Test
    fun `interactor calls repository with incoming coordinate arguments`() {
        mainCoroutineRule.runBlockingTest {
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
    }

    @Test
    fun `interactor maps network data to interactor today weather`() {
        mainCoroutineRule.runBlockingTest {
            // pre
            val interactor = GetTodayWeatherInteractorImpl(weatherRepository)

            // when we execute the interactor query
            val response = interactor.execute(incomingCity)

            // the response has been converted to the interactor type
            Assert.assertTrue(response.isSuccess)
            assertEquals(response.getOrNull(), successfulTodayWeather)
        }
    }

    @Test
    fun `interactor maps network data to interactor forecast weather`() {
        mainCoroutineRule.runBlockingTest {
            // pre
            val interactor = GetTodayWeatherInteractorImpl(weatherRepository)

            // when we execute the interactor query
            val response = interactor.execute(incomingCity)

            // the response has been converted to the interactor type
            Assert.assertTrue(response.isSuccess)
            assertEquals(response.getOrNull(), successfulTodayWeather)
        }
    }

    @Test
    fun `interactor maps network error to interactor error`() {
        mainCoroutineRule.runBlockingTest {
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
}
