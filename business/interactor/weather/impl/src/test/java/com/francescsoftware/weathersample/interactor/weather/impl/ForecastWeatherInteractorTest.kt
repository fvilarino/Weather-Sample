package com.francescsoftware.weathersample.interactor.weather.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.francescsoftware.weathersample.interactor.weather.api.Forecast
import com.francescsoftware.weathersample.interactor.weather.api.ForecastDay
import com.francescsoftware.weathersample.interactor.weather.api.ForecastEntry
import com.francescsoftware.weathersample.interactor.weather.api.WeatherException
import com.francescsoftware.weathersample.testing.MainCoroutineRule
import com.francescsoftware.weathersample.testing.runBlockingTest
import com.francescsoftware.weathersample.testing.testDispatcherProvider
import com.francescsoftware.weathersample.time.api.TimeFormatter
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
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.City
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.ForecastItem
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.ForecastResponse
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
import java.util.*
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

private const val todayEpoch = 1618938000
private const val todayEpochMidnight = 1618902000
private const val todaySunrise = 1618922000
private const val todaySunset = 1618972000
private const val oneHourSeconds = 60 * 60
private const val oneHourMillis = 60L * 60L * 1000L

@ExperimentalCoroutinesApi
class ForecastWeatherInteractorTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var weatherRepository: WeatherRepository

    private val timeFormatter = object : TimeFormatter {
        override fun setToMidnight(date: Date): Date = Calendar.getInstance().let { calendar ->
            calendar.time = date
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.time
        }

        override fun formatDayHour(date: Date): String = date.toString()

        override fun formatDayWithDayOfWeek(date: Date): String = date.toString()

        override fun formatDay(date: Date): String = date.toString()

        override fun formatHour(date: Date): String = date.toString()
    }

    private val todayForecast1 = ForecastItem(
        epoch = todayEpoch,
        visibility = visibilityMeters,
        weather = listOf(
            WeatherItem(
                icon = iconId,
                description = weatherDescription,
                main = weatherMain,
            )
        ),
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
        wind = Wind(
            deg = windDirection,
            speed = windSpeed,
        ),
    )

    private val todayForecast2 = todayForecast1.copy(
        epoch = todayEpoch + oneHourSeconds,
    )

    private val forecastRepositoryResponse = ForecastResponse(
        city = City(
            sunrise = todaySunrise,
            sunset = todaySunset,
        ),
        forecast = listOf(
            todayForecast1,
            todayForecast2,
        )
    )

    private val successfulForecast1Entry1 = ForecastEntry(
        date = Date(todayEpoch * 1000L),
        description = weatherDescription,
        icon = iconId,
        minTemperature = minTemperature,
        maxTemperature = maxTemperature,
        feelsLikeTemperature = feelsLikeTemperature,
        windSpeed = windSpeed,
        humidityPercent = humidityPercent,
        visibility = visibilityMeters,
    )

    private val successfulForecast1Entry2 = successfulForecast1Entry1.copy(
        date = Date(todayEpoch * 1000L + oneHourMillis)
    )

    private val successfulForecastDay1 = ForecastDay(
        date = Date(todayEpochMidnight * 1000L),
        sunrise = Date(todaySunrise * 1000L),
        sunset = Date(todaySunset * 1000L),
        entries = listOf(
            successfulForecast1Entry1,
            successfulForecast1Entry2,
        )
    )

    private val forecastSuccessfulResponse = Forecast(
        items = listOf(
            successfulForecastDay1,
        )
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
        coEvery { weatherRepository.getForecast(any()) } returns Result.Success(
            forecastRepositoryResponse
        )
    }

    @Test
    fun `interactor calls repository with incoming city arguments`() {
        mainCoroutineRule.runBlockingTest {
            // pre
            val interactor = GetForecastInteractorImpl(
                weatherRepository,
                testDispatcherProvider,
                timeFormatter
            )

            // when we execute the interactor query
            interactor.execute(incomingCity)

            // we call the repository once with the same argument
            coVerify(exactly = 1) {
                weatherRepository.getForecast(
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
            val interactor = GetForecastInteractorImpl(
                weatherRepository,
                testDispatcherProvider,
                timeFormatter
            )

            // when we execute the interactor query
            interactor.execute(incomingCoordinates)

            // we call the repository once with the same argument
            coVerify(exactly = 1) {
                weatherRepository.getForecast(
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
            val interactor = GetForecastInteractorImpl(
                weatherRepository,
                testDispatcherProvider,
                timeFormatter
            )

            // when we execute the interactor query
            val response = interactor.execute(incomingCity)

            // the response has been converted to the interactor type
            Assert.assertTrue(response.isSuccess)
            assertEquals(response.getOrNull(), forecastSuccessfulResponse)
        }
    }

    @Test
    fun `interactor maps network error to interactor error`() {
        mainCoroutineRule.runBlockingTest {
            // pre
            val interactor = GetForecastInteractorImpl(
                weatherRepository,
                testDispatcherProvider,
                timeFormatter
            )
            coEvery {
                weatherRepository.getForecast(any())
            } returns Result.Failure(IOException("Failed to load weather"))

            // when we execute the interactor query
            val response = interactor.execute(incomingCity)

            // the response has been converted to the interactor type
            Assert.assertTrue(response.isFailure)
            Assert.assertTrue(response.throwableOrNull() is WeatherException)
        }
    }
}
