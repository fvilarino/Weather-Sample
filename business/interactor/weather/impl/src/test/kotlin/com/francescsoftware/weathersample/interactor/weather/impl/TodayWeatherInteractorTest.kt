package com.francescsoftware.weathersample.interactor.weather.impl

import com.francescsoftware.weathersample.dispatcher.TestDispatcherProvider
import com.francescsoftware.weathersample.interactor.weather.api.TodayClouds
import com.francescsoftware.weathersample.interactor.weather.api.TodayMain
import com.francescsoftware.weathersample.interactor.weather.api.TodayWeather
import com.francescsoftware.weathersample.interactor.weather.api.TodayWind
import com.francescsoftware.weathersample.interactor.weather.api.WeatherException
import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.type.isFailure
import com.francescsoftware.weathersample.type.isSuccess
import com.francescsoftware.weathersample.type.throwableOrNull
import com.francescsoftware.weathersample.type.valueOrNull
import com.francescsoftware.weathersample.weatherrepository.api.model.Condition
import com.francescsoftware.weathersample.weatherrepository.api.model.Current
import com.francescsoftware.weathersample.weatherrepository.api.model.Location
import com.francescsoftware.weathersample.weatherrepository.api.model.today.TodayWeatherResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
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

    private val emptyLocation = Location(
        localtime = "",
        localtimeEpoch = 0,
        name = "",
        region = "",
        country = "",
        latitude = 0.0,
        longitude = 0.0,
        timezoneId = "",
    )

    private val todayWeatherResponse = TodayWeatherResponse(
        location = emptyLocation,
        current = Current(
            isDay = 1,
            lastUpdated = "",
            lastUpdatedEpoch = 0,
            condition = Condition(
                code = ConditionCode,
                icon = "",
                text = WeatherDescription,
            ),
            tempCelsius = CurrentTemperature,
            tempFahrenheit = CurrentTemperature * 1.8 + 32.0,
            feelsLikeCelsius = FeelsLikeTemperature,
            feelsLikeFahrenheit = FeelsLikeTemperature * 1.8 + 32.0,
            uvIndex = UvIndex.toDouble(),
            precipitationMm = Precipitation.toDouble(),
            precipitationInches = 0.0,
            humidity = HumidityPercent,
            pressureMb = PressureMb,
            pressureIn = 0.0,
            windDirection = "N",
            windDegree = 0,
            windKph = WindSpeed,
            windMph = 0.0,
            gustKph = GustSpeed,
            gustMph = 0.0,
            cloud = Clouds,
            visibilityKm = VisibilityKilometers.toDouble(),
            visibilityMiles = 0.0,
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

    private val incomingCity: WeatherLocation.City = WeatherLocation.City(
        name = CityName,
        countryCode = CountryCode,
    )

    private val incomingCoordinates: WeatherLocation.Coordinates = WeatherLocation.Coordinates(
        latitude = CityLatitude,
        longitude = CityLongitude,
    )

    @Test
    fun `interactor calls repository with incoming city arguments`() = runTest {
        val repository = FakeWeatherRepository().apply {
            todayResponse = todayWeatherResponse
        }
        val interactor = GetTodayWeatherInteractorImpl(
            weatherRepository = repository,
            dispatcherProvider = TestDispatcherProvider(),
        )

        interactor.execute(incomingCity)

        Assertions.assertEquals(
            (repository.lastLocation as RepositoryLocation.City).name,
            incomingCity.name
        )
        Assertions.assertEquals(
            (repository.lastLocation as RepositoryLocation.City).countryCode,
            incomingCity.countryCode
        )
    }

    @Test
    fun `interactor calls repository with incoming coordinate arguments`() = runTest {
        val repository = FakeWeatherRepository().apply {
            todayResponse = todayWeatherResponse
        }
        val interactor = GetTodayWeatherInteractorImpl(
            weatherRepository = repository,
            dispatcherProvider = TestDispatcherProvider(),
        )

        interactor.execute(incomingCoordinates)

        Assertions.assertEquals(
            (repository.lastLocation as RepositoryLocation.Coordinates).latitude,
            incomingCoordinates.latitude
        )
        Assertions.assertEquals(
            (repository.lastLocation as RepositoryLocation.Coordinates).longitude,
            incomingCoordinates.longitude
        )
    }

    @Test
    fun `interactor maps network data to interactor today weather`() = runTest {
        val repository = FakeWeatherRepository().apply {
            todayResponse = todayWeatherResponse
        }
        val interactor = GetTodayWeatherInteractorImpl(
            weatherRepository = repository,
            dispatcherProvider = TestDispatcherProvider(),
        )
        val response = interactor.execute(incomingCity)

        Assertions.assertTrue(response.isSuccess)
        Assertions.assertEquals(response.valueOrNull(), successfulTodayWeather)
    }

    @Test
    fun `interactor maps network data to interactor forecast weather`() = runTest {
        val repository = FakeWeatherRepository().apply {
            todayResponse = todayWeatherResponse
        }
        val interactor = GetTodayWeatherInteractorImpl(
            weatherRepository = repository,
            dispatcherProvider = TestDispatcherProvider(),
        )
        val response = interactor.execute(incomingCity)

        Assertions.assertTrue(response.isSuccess)
        Assertions.assertEquals(response.valueOrNull(), successfulTodayWeather)
    }

    @Test
    fun `interactor maps network error to interactor error`() = runTest {
        val repository = FakeWeatherRepository().apply {
            networkError = true
        }
        val interactor = GetTodayWeatherInteractorImpl(
            weatherRepository = repository,
            dispatcherProvider = TestDispatcherProvider(),
        )
        val response = interactor.execute(incomingCity)

        Assertions.assertTrue(response.isFailure)
        Assertions.assertTrue(response.throwableOrNull() is WeatherException)
    }
}
