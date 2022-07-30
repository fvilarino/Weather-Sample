package com.francescsoftware.weathersample.interactor.weather.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.francescsoftware.weathersample.interactor.weather.api.Forecast
import com.francescsoftware.weathersample.interactor.weather.api.ForecastDay
import com.francescsoftware.weathersample.interactor.weather.api.ForecastEntry
import com.francescsoftware.weathersample.interactor.weather.api.WeatherException
import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.testing.MainCoroutineRule
import com.francescsoftware.weathersample.testing.testDispatcherProvider
import com.francescsoftware.weathersample.time.api.TimeFormatter
import com.francescsoftware.weathersample.type.Result
import com.francescsoftware.weathersample.type.getOrNull
import com.francescsoftware.weathersample.type.isFailure
import com.francescsoftware.weathersample.type.isSuccess
import com.francescsoftware.weathersample.type.throwableOrNull
import com.francescsoftware.weathersample.weatherrepository.api.WeatherRepository
import com.francescsoftware.weathersample.weatherrepository.api.model.Condition
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.Astro
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.ForecastDayItem
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.ForecastResponse
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.HourItem
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
import java.util.Calendar
import java.util.Date
import com.francescsoftware.weathersample.weatherrepository.api.WeatherLocation as RepositoryLocation
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.Forecast as RepositoryForecast

private const val cityName = "Vancouver"
private const val countryCode = "CA"
private const val CityLatitude = 49.24
private const val CityLongitude = -123.11

private const val date = "2022-07-30"
private const val dateEpoch = 1659132000
private const val timeEpoch = 1659135600
private const val sunrise = "06:44 AM"
private const val sunset = "09:11 PM"

private val temperature = listOf(11.5, 13.7)
private val feelsLikeTemperature = listOf(9.5, 11.5)
private val iconCode = listOf(1000, 1240)
private val precipitation = listOf(14, 23)
private val uvIndex = listOf(3, 5)
private val humidityPercent = listOf(54, 71)
private val visibilityKilometers = listOf(3, 4)
private val weatherDescription = listOf("sunny", "partly cloudy")
private val windSpeed = listOf(4.5, 3.8)

private const val oneDayInSeconds = 24 * 60 * 60
private const val oneHourSeconds = 60 * 60

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

    private val forecastHour1 = HourItem(
        timeEpoch = timeEpoch,
        tempC = temperature[0],
        feelslikeC = feelsLikeTemperature[0],
        precipMm = precipitation[0].toDouble(),
        windKph = windSpeed[0],
        humidity = humidityPercent[0],
        visKm = visibilityKilometers[0].toDouble(),
        uv = uvIndex[0].toDouble(),
        condition = Condition(
            code = iconCode[0],
            icon = "",
            text = weatherDescription[0],
        )
    )

    private val forecastHour2 = HourItem(
        timeEpoch = timeEpoch + oneHourSeconds,
        tempC = temperature[1],
        feelslikeC = feelsLikeTemperature[1],
        precipMm = precipitation[1].toDouble(),
        windKph = windSpeed[1],
        humidity = humidityPercent[1],
        visKm = visibilityKilometers[1].toDouble(),
        uv = uvIndex[1].toDouble(),
        condition = Condition(
            code = iconCode[1],
            icon = "",
            text = weatherDescription[1],
        )
    )

    private val todayForecast1 = ForecastDayItem(
        date = date,
        dateEpoch = dateEpoch,
        astro = Astro(
            sunrise = sunrise,
            sunset = sunset,
        ),
        hour = listOf(
            forecastHour1,
            forecastHour2,
        ),
    )

    private val todayForecast2 = todayForecast1.copy(
        dateEpoch = dateEpoch + oneDayInSeconds,
        hour = listOf(
            todayForecast1.hour!!.first().copy(
                timeEpoch = timeEpoch + oneHourSeconds
            )
        )
    )

    private val forecastRepositoryResponse = ForecastResponse(
        forecast = RepositoryForecast(
            forecastDay = listOf(
                todayForecast1,
            )
        )
    )

    private val successfulForecast1Entry1 = ForecastEntry(
        date = Date(timeEpoch * 1000L),
        description = weatherDescription[0],
        iconCode = iconCode[0],
        temperature = temperature[0],
        feelsLikeTemperature = feelsLikeTemperature[0],
        precipitation = precipitation[0],
        uvIndex = uvIndex[0],
        windSpeed = windSpeed[0],
        humidityPercent = humidityPercent[0],
        visibility = visibilityKilometers[0],
    )

    private val successfulForecast1Entry2 = ForecastEntry(
        date = Date((timeEpoch + oneHourSeconds) * 1000L),
        description = weatherDescription[1],
        iconCode = iconCode[1],
        temperature = temperature[1],
        feelsLikeTemperature = feelsLikeTemperature[1],
        precipitation = precipitation[1],
        uvIndex = uvIndex[1],
        windSpeed = windSpeed[1],
        humidityPercent = humidityPercent[1],
        visibility = visibilityKilometers[1],
    )

    private val successfulForecastDay1 = ForecastDay(
        date = timeFormatter.setToMidnight(Date(dateEpoch * 1000L)),
        sunrise = sunrise,
        sunset = sunset,
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

    private val incomingCity = WeatherLocation.City(
        name = cityName,
        countryCode = countryCode,
    )

    private val incomingCoordinates = WeatherLocation.Coordinates(
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
    fun `interactor calls repository with incoming city arguments`() = runTest {
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

    @Test
    fun `interactor calls repository with incoming coordinate arguments`() = runTest {
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
                    assertEquals(queryCoordinates, arg)
                }
            )
        }
    }

    @Test
    fun `interactor maps network data to interactor today weather`() = runTest {
        // pre
        val interactor = GetForecastInteractorImpl(
            weatherRepository,
            testDispatcherProvider,
            timeFormatter,
        )

        // when we execute the interactor query
        val response = interactor.execute(incomingCity)

        // the response has been converted to the interactor type
        Assert.assertTrue(response.isSuccess)
        assertEquals(forecastSuccessfulResponse, response.getOrNull())
    }

    @Test
    fun `interactor maps network error to interactor error`() = runTest {
        // pre
        val interactor = GetForecastInteractorImpl(
            weatherRepository,
            testDispatcherProvider,
            timeFormatter,
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
