package com.francescsoftware.weathersample.interactor.weather.impl

import com.francescsoftware.weathersample.dispatcher.TestDispatcherProvider
import com.francescsoftware.weathersample.interactor.weather.api.Forecast
import com.francescsoftware.weathersample.interactor.weather.api.ForecastDay
import com.francescsoftware.weathersample.interactor.weather.api.ForecastEntry
import com.francescsoftware.weathersample.interactor.weather.api.WeatherException
import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.time.api.Iso8601DateTime
import com.francescsoftware.weathersample.time.impl.FakeTimeFormatter
import com.francescsoftware.weathersample.time.impl.FakeTimeParser
import com.francescsoftware.weathersample.type.Either
import com.francescsoftware.weathersample.type.isFailure
import com.francescsoftware.weathersample.type.isSuccess
import com.francescsoftware.weathersample.type.throwableOrNull
import com.francescsoftware.weathersample.type.valueOrNull
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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException
import java.util.Date
import com.francescsoftware.weathersample.weatherrepository.api.WeatherLocation as RepositoryLocation
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.Forecast as RepositoryForecast

private const val CityName = "Vancouver"
private const val CountryCode = "CA"
private const val CityLatitude = 49.24
private const val CityLongitude = -123.11

private const val Date = "2022-07-30"
private const val DateEpoch = 1659132000
private const val Sunrise = "06:44 AM"
private const val Sunset = "09:11 PM"

private val ForecastTime = listOf("2022-07-29 16:00", "2022-07-29 17:00")
private val Temperature = listOf(11.5, 13.7)
private val FeelsLikeTemperature = listOf(9.5, 11.5)
private val IconCode = listOf(1000, 1240)
private val Precipitation = listOf(14, 23)
private val UvIndex = listOf(3, 5)
private val HumidityPercent = listOf(54, 71)
private val VisibilityKilometers = listOf(3, 4)
private val WeatherDescription = listOf("sunny", "partly cloudy")
private val WindSpeed = listOf(4.5, 3.8)

@ExperimentalCoroutinesApi
class ForecastWeatherInteractorTest {

    @MockK
    lateinit var weatherRepository: WeatherRepository

    private val timeFormatter = FakeTimeFormatter()
    private val timeParser = FakeTimeParser()

    private val forecastIsoTime = ForecastTime.map { Iso8601DateTime(it) }
    private val forecastHour1 = HourItem(
        time = forecastIsoTime[0].date,
        timeEpoch = timeParser.parseDate(forecastIsoTime[0]).time.toInt(),
        tempC = Temperature[0],
        feelslikeC = FeelsLikeTemperature[0],
        precipMm = Precipitation[0].toDouble(),
        windKph = WindSpeed[0],
        humidity = HumidityPercent[0],
        visKm = VisibilityKilometers[0].toDouble(),
        uv = UvIndex[0].toDouble(),
        condition = Condition(
            code = IconCode[0],
            icon = "",
            text = WeatherDescription[0],
        )
    )

    private val forecastHour2 = HourItem(
        time = forecastIsoTime[1].date,
        timeEpoch = timeParser.parseDate(forecastIsoTime[1]).time.toInt(),
        tempC = Temperature[1],
        feelslikeC = FeelsLikeTemperature[1],
        precipMm = Precipitation[1].toDouble(),
        windKph = WindSpeed[1],
        humidity = HumidityPercent[1],
        visKm = VisibilityKilometers[1].toDouble(),
        uv = UvIndex[1].toDouble(),
        condition = Condition(
            code = IconCode[1],
            icon = "",
            text = WeatherDescription[1],
        )
    )

    private val todayForecast1 = ForecastDayItem(
        date = Date,
        dateEpoch = DateEpoch,
        astro = Astro(
            sunrise = Sunrise,
            sunset = Sunset,
        ),
        hour = listOf(
            forecastHour1,
            forecastHour2,
        ),
    )

    private val forecastRepositoryResponse = ForecastResponse(
        forecast = RepositoryForecast(
            forecastDay = listOf(
                todayForecast1,
            )
        )
    )

    private val successfulForecast1Entry1 = ForecastEntry(
        date = timeParser.parseDate(forecastIsoTime[0]),
        description = WeatherDescription[0],
        iconCode = IconCode[0],
        temperature = Temperature[0],
        feelsLikeTemperature = FeelsLikeTemperature[0],
        precipitation = Precipitation[0],
        uvIndex = UvIndex[0],
        windSpeed = WindSpeed[0],
        humidityPercent = HumidityPercent[0],
        visibility = VisibilityKilometers[0],
    )

    private val successfulForecast1Entry2 = ForecastEntry(
        date = timeParser.parseDate(forecastIsoTime[1]),
        description = WeatherDescription[1],
        iconCode = IconCode[1],
        temperature = Temperature[1],
        feelsLikeTemperature = FeelsLikeTemperature[1],
        precipitation = Precipitation[1],
        uvIndex = UvIndex[1],
        windSpeed = WindSpeed[1],
        humidityPercent = HumidityPercent[1],
        visibility = VisibilityKilometers[1],
    )

    private val successfulForecastDay1 = ForecastDay(
        date = timeFormatter.setToMidnight(Date(DateEpoch * 1000L)),
        sunrise = Sunrise,
        sunset = Sunset,
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
        name = CityName,
        countryCode = CountryCode,
    )

    private val incomingCoordinates = WeatherLocation.Coordinates(
        latitude = CityLatitude,
        longitude = CityLongitude,
    )

    private val queryCity = RepositoryLocation.City(
        name = CityName,
        countryCode = CountryCode,
    )

    private val queryCoordinates = RepositoryLocation.Coordinates(
        latitude = CityLatitude,
        longitude = CityLongitude,
    )

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        coEvery { weatherRepository.getForecast(any()) } returns Either.Success(
            forecastRepositoryResponse
        )
    }

    @Test
    fun `interactor calls repository with incoming city arguments`() = runTest {
        // pre
        val interactor = GetForecastInteractorImpl(
            weatherRepository,
            TestDispatcherProvider(),
            timeFormatter,
            timeParser,
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
            TestDispatcherProvider(),
            timeFormatter,
            timeParser,
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
            TestDispatcherProvider(),
            timeFormatter,
            timeParser,
        )

        // when we execute the interactor query
        val response = interactor.execute(incomingCity)

        // the response has been converted to the interactor type
        assertTrue(response.isSuccess)
        assertEquals(forecastSuccessfulResponse, response.valueOrNull())
    }

    @Test
    fun `interactor maps network error to interactor error`() = runTest {
        // pre
        val interactor = GetForecastInteractorImpl(
            weatherRepository,
            TestDispatcherProvider(),
            timeFormatter,
            timeParser,
        )
        coEvery {
            weatherRepository.getForecast(any())
        } returns Either.Failure(IOException("Failed to load weather"))

        // when we execute the interactor query
        val response = interactor.execute(incomingCity)

        // the response has been converted to the interactor type
        assertTrue(response.isFailure)
        assertTrue(response.throwableOrNull() is WeatherException)
    }
}