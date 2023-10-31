package com.francescsoftware.weathersample.domain.interactor.weather.impl

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import com.francescsoftware.weathersample.core.dispatcher.TestDispatcherProvider
import com.francescsoftware.weathersample.core.type.either.isFailure
import com.francescsoftware.weathersample.core.type.either.isSuccess
import com.francescsoftware.weathersample.core.type.either.throwableOrNull
import com.francescsoftware.weathersample.core.type.either.valueOrNull
import com.francescsoftware.weathersample.core.type.weather.AverageVisibility
import com.francescsoftware.weathersample.core.type.weather.Humidity
import com.francescsoftware.weathersample.core.type.weather.Precipitation
import com.francescsoftware.weathersample.core.type.weather.Pressure
import com.francescsoftware.weathersample.core.type.weather.Speed
import com.francescsoftware.weathersample.core.type.weather.Temperature
import com.francescsoftware.weathersample.core.type.weather.UvIndex
import com.francescsoftware.weathersample.data.repository.weather.api.FakeWeatherRepository
import com.francescsoftware.weathersample.data.repository.weather.api.model.Condition
import com.francescsoftware.weathersample.data.repository.weather.api.model.Current
import com.francescsoftware.weathersample.data.repository.weather.api.model.Location
import com.francescsoftware.weathersample.data.repository.weather.api.model.today.TodayWeatherResponse
import com.francescsoftware.weathersample.domain.interactor.weather.api.GetTodayWeatherInteractor
import com.francescsoftware.weathersample.domain.interactor.weather.api.WeatherException
import com.francescsoftware.weathersample.domain.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.TodayClouds
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.TodayMain
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.TodayWeather
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.TodayWind
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import com.francescsoftware.weathersample.data.repository.weather.api.WeatherLocation.City as RepoCity
import com.francescsoftware.weathersample.data.repository.weather.api.WeatherLocation.Coordinates as RepoCoordinates

private const val CityName = "Vancouver"
private const val CountryCode = "CA"
private const val CityLatitude = 49.24
private const val CityLongitude = -123.11

private const val ConditionCode = 1048
private const val CurrentTemperature = 23.0
private const val PrecipitationMillis = 14.0
private const val FeelsLikeTemperature = 21.4
private const val HumidityPercent = 54
private const val PressureMb = 1024.0
private const val VisibilityKilometers = 10.0
private const val Clouds = 32
private const val WeatherDescription = "sunny"
private const val WindSpeed = 4.5
private const val GustSpeed = 8.7
private const val WindDirection = "N"
private const val UvIndex = 7

@ExperimentalCoroutinesApi
internal class TodayWeatherInteractorTest {

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
            precipitationMm = PrecipitationMillis,
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
            visibilityKm = VisibilityKilometers,
            visibilityMiles = 0.0,
        ),
    )

    private val successfulTodayWeather = TodayWeather(
        main = TodayMain(
            description = WeatherDescription,
            temperature = Temperature.fromCelsius(CurrentTemperature),
            feelsLike = Temperature.fromCelsius(FeelsLikeTemperature),
            humidity = Humidity(HumidityPercent),
            pressure = Pressure.fromMillibars(PressureMb),
            precipitation = Precipitation.fromMillimeters(PrecipitationMillis),
            code = ConditionCode,
            uvIndex = UvIndex(UvIndex),
        ),
        wind = TodayWind(
            direction = WindDirection,
            speed = Speed.fromKph(WindSpeed),
            gust = Speed.fromKph(GustSpeed),
        ),
        clouds = TodayClouds(
            all = Clouds,
        ),
        visibility = AverageVisibility.fromKm(VisibilityKilometers),
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

        interactor(GetTodayWeatherInteractor.Params(incomingCity))

        assertThat((repository.lastLocation as RepoCity).name).isEqualTo(incomingCity.name)
        assertThat((repository.lastLocation as RepoCity).countryCode).isEqualTo(incomingCity.countryCode)
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

        interactor(GetTodayWeatherInteractor.Params(incomingCoordinates))

        assertThat((repository.lastLocation as RepoCoordinates).latitude)
            .isEqualTo(incomingCoordinates.latitude)
        assertThat((repository.lastLocation as RepoCoordinates).longitude)
            .isEqualTo(incomingCoordinates.longitude)
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
        val response = interactor(GetTodayWeatherInteractor.Params(incomingCity))

        assertThat(response.isSuccess).isTrue()
        assertThat(response.valueOrNull()).isEqualTo(successfulTodayWeather)
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
        val response = interactor(GetTodayWeatherInteractor.Params(incomingCity))

        assertThat(response.isSuccess).isTrue()
        assertThat(response.valueOrNull()).isEqualTo(successfulTodayWeather)
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
        val response = interactor(GetTodayWeatherInteractor.Params(incomingCity))

        assertThat(response.isFailure).isTrue()
        assertThat(response.throwableOrNull()).isNotNull().isInstanceOf<WeatherException>()
    }
}
