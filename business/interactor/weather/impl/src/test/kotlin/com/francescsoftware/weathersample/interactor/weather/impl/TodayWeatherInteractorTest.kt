package com.francescsoftware.weathersample.interactor.weather.impl

import com.francescsoftware.weathersample.interactor.weather.api.TodayClouds
import com.francescsoftware.weathersample.interactor.weather.api.TodayMain
import com.francescsoftware.weathersample.interactor.weather.api.TodayWeather
import com.francescsoftware.weathersample.interactor.weather.api.TodayWind
import com.francescsoftware.weathersample.interactor.weather.api.WeatherException
import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.type.Either
import com.francescsoftware.weathersample.type.isFailure
import com.francescsoftware.weathersample.type.isSuccess
import com.francescsoftware.weathersample.type.throwableOrNull
import com.francescsoftware.weathersample.type.valueOrNull
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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException
import kotlin.math.roundToInt
import com.francescsoftware.weathersample.weatherrepository.api.WeatherLocation as RepositoryLocation

private const val CityName = "Vancouver"
private const val CountryCode = "CA"
private const val CityLatitude = 49.24
private const val CityLongitude = -123.11

private const val ConditionCode = 1048
private const val CurrentTemperature = 23.0
private const val Precipitation = 14
private const val FeelsLikeTemperature = 21.4
private const val HumidityPercent = 54
private const val PressureMb = 1024.0
private const val VisibilityKilometers = 10
private const val Clouds = 32
private const val WeatherDescription = "sunny"
private const val WindSpeed = 4.5
private const val GustSpeed = 8.7
private const val WindDirection = "N"
private const val UvIndex = 7

@ExperimentalCoroutinesApi
class TodayWeatherInteractorTest {

    @MockK
    lateinit var weatherRepository: WeatherRepository

    private val todayWeatherResponse = TodayWeatherResponse(
        current = Current(
            condition = Condition(
                code = ConditionCode,
                icon = "",
                text = WeatherDescription,
            ),
            tempC = CurrentTemperature,
            tempF = CurrentTemperature * 1.8 + 32.0,
            feelslikeC = FeelsLikeTemperature,
            feelslikeF = FeelsLikeTemperature * 1.8 + 32.0,
            uv = UvIndex.toDouble(),
            precipMm = Precipitation.toDouble(),
            humidity = HumidityPercent,
            pressureMb = PressureMb,
            windDir = "N",
            windKph = WindSpeed,
            gustKph = GustSpeed,
            cloud = Clouds,
            visKm = VisibilityKilometers.toDouble()
        )
    )

    private val successfulTodayWeather = TodayWeather(
        main = TodayMain(
            description = WeatherDescription,
            temp = CurrentTemperature,
            feelsLike = FeelsLikeTemperature,
            humidity = HumidityPercent,
            pressure = PressureMb.roundToInt(),
            precipitation = Precipitation,
            code = ConditionCode,
            uvIndex = UvIndex,
        ),
        wind = TodayWind(
            direction = WindDirection,
            speed = WindSpeed,
            gust = GustSpeed,
        ),
        clouds = TodayClouds(
            all = Clouds,
        ),
        visibility = VisibilityKilometers,
    )

    private val incomingCity: WeatherLocation = WeatherLocation.City(
        name = CityName,
        countryCode = CountryCode,
    )

    private val incomingCoordinates: WeatherLocation = WeatherLocation.Coordinates(
        latitude = CityLatitude,
        longitude = CityLongitude,
    )

    private val queryCity: RepositoryLocation = RepositoryLocation.City(
        name = CityName,
        countryCode = CountryCode,
    )

    private val queryCoordinates: RepositoryLocation = RepositoryLocation.Coordinates(
        latitude = CityLatitude,
        longitude = CityLongitude,
    )

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        coEvery { weatherRepository.getTodayWeather(any()) } returns Either.Success(
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
        assertTrue(response.isSuccess)
        assertEquals(response.valueOrNull(), successfulTodayWeather)
    }

    @Test
    fun `interactor maps network data to interactor forecast weather`() = runTest {
        // pre
        val interactor = GetTodayWeatherInteractorImpl(weatherRepository)

        // when we execute the interactor query
        val response = interactor.execute(incomingCity)

        // the response has been converted to the interactor type
        assertTrue(response.isSuccess)
        assertEquals(response.valueOrNull(), successfulTodayWeather)
    }

    @Test
    fun `interactor maps network error to interactor error`() = runTest {
        // pre
        val interactor = GetTodayWeatherInteractorImpl(weatherRepository)
        coEvery {
            weatherRepository.getTodayWeather(any())
        } returns Either.Failure(IOException("Failed to load today weather"))

        // when we execute the interactor query
        val response = interactor.execute(incomingCity)

        // the response has been converted to the interactor type
        assertTrue(response.isFailure)
        assertTrue(response.throwableOrNull() is WeatherException)
    }
}
