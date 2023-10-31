package com.francescsoftware.weathersample.domain.interactor.weather.impl

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import com.francescsoftware.weathersample.core.time.api.Iso8601DateTime
import com.francescsoftware.weathersample.core.time.api.TimeProvider
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
import com.francescsoftware.weathersample.data.repository.weather.api.WeatherRepository
import com.francescsoftware.weathersample.data.repository.weather.api.model.Condition
import com.francescsoftware.weathersample.data.repository.weather.api.model.Location
import com.francescsoftware.weathersample.data.repository.weather.api.model.forecast.Astro
import com.francescsoftware.weathersample.data.repository.weather.api.model.forecast.Day
import com.francescsoftware.weathersample.data.repository.weather.api.model.forecast.ForecastHour
import com.francescsoftware.weathersample.data.repository.weather.api.model.forecast.ForecastResponse
import com.francescsoftware.weathersample.domain.interactor.weather.api.GetForecastInteractor
import com.francescsoftware.weathersample.domain.interactor.weather.api.WeatherException
import com.francescsoftware.weathersample.domain.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.Current
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.Forecast
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.ForecastDay
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.ForecastEntry
import com.francescsoftware.weathersample.testing.fake.dispatcher.TestDispatcherProvider
import com.francescsoftware.weathersample.testing.fake.time.FakeTimeProvider
import com.francescsoftware.weathersample.testing.fake.time.FakeZoneIdProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.ZoneId
import com.francescsoftware.weathersample.data.repository.weather.api.WeatherLocation as RepoWeatherLocation
import com.francescsoftware.weathersample.data.repository.weather.api.model.Current as RepoCurrent
import com.francescsoftware.weathersample.data.repository.weather.api.model.forecast.Forecast as RepoForecast
import com.francescsoftware.weathersample.data.repository.weather.api.model.forecast.ForecastDay as RepoForecastDay

private const val CityName = "Vancouver"
private const val CountryCode = "CA"
private const val CityLatitude = 49.24
private const val CityLongitude = -123.11

private const val Date = "2022-07-30"
private const val DateEpoch = 1659052800
private const val Sunrise = "06:44 AM"
private const val Sunset = "09:11 PM"

private const val CurrentTemperature = 17.4
private const val CurrentFeelsLike = 15.1
private const val CurrentWind = 3.4
private const val CurrentGust = 6.3
private const val CurrentHumidity = 62
private const val CurrentPressure = 1024.0
private const val CurrentPrecipitation = 1.8
private const val CurrentUvIndex = 7
private const val CurrentVisibility = 10.0

private val ForecastTimes = listOf("2022-07-29 15:00", "2022-07-29 16:00")
private val Temperatures = listOf(11.5, 13.7)
private val FeelsLikeTemperatures = listOf(9.5, 11.5)
private val IconCodes = listOf(1000, 1240)
private val Precipitations = listOf(14.0, 23.3)
private val UvIndexes = listOf(3, 5)
private val HumidityPercents = listOf(54, 71)
private val VisibilityKilometers = listOf(3.4, 4.7)
private val WeatherDescriptions = listOf("sunny", "partly cloudy")
private val WindSpeeds = listOf(4.5, 3.8)

private val UtcZoneId = ZoneId.of("UTC")

@ExperimentalCoroutinesApi
internal class ForecastWeatherInteractorTest {

    private val timeProvider = FakeTimeProvider()
    private val zoneIdProvider = FakeZoneIdProvider().apply { currentZoneId = UtcZoneId }

    private val forecastIsoTime = ForecastTimes.map { Iso8601DateTime(it) }
    private val forecastHour1 = ForecastHour(
        isDay = 1,
        time = forecastIsoTime[0].date,
        timeEpoch = forecastIsoTime[0].toZonedDateTime(zoneId = UtcZoneId).toEpochSecond().toInt(),
        tempCelsius = Temperatures[0],
        tempFahrenheit = 0.0,
        feelsLikeCelsius = FeelsLikeTemperatures[0],
        feelsLikeFahrenheit = 0.0,
        windChillCelsius = 0.0,
        willChillFahrenheit = 0.0,
        windDirection = "",
        windDegree = 0,
        windKph = WindSpeeds[0],
        windMph = 0.0,
        gustKph = 0.0,
        gustMph = 0.0,
        cloud = 0,
        humidity = HumidityPercents[0],
        dewPointCelsius = 0.0,
        dewPointFahrenheit = 0.0,
        uvIndex = UvIndexes[0].toDouble(),
        headIndexCelsius = 0.0,
        heatIndexFahrenheit = 0.0,
        willItRain = 0,
        chanceOfRain = 0,
        precipitationMm = Precipitations[0],
        precipitationInches = 0.0,
        condition = Condition(
            code = IconCodes[0],
            icon = "",
            text = WeatherDescriptions[0],
        ),
        willItSnow = 0,
        chanceOfSnow = 0,
        pressureMb = 0.0,
        pressureIn = 0.0,
        visibilityKm = VisibilityKilometers[0],
        visibilityMiles = 0.0,
    )

    private val forecastHour2 = ForecastHour(
        isDay = 1,
        time = forecastIsoTime[1].date,
        timeEpoch = forecastIsoTime[1].toZonedDateTime(zoneId = UtcZoneId).toEpochSecond().toInt(),
        tempCelsius = Temperatures[1],
        feelsLikeCelsius = FeelsLikeTemperatures[1],
        precipitationMm = Precipitations[1],
        windKph = WindSpeeds[1],
        humidity = HumidityPercents[1],
        visibilityKm = VisibilityKilometers[1],
        uvIndex = UvIndexes[1].toDouble(),
        condition = Condition(
            code = IconCodes[1],
            icon = "",
            text = WeatherDescriptions[1],
        ),
        tempFahrenheit = 0.0,
        feelsLikeFahrenheit = 0.0,
        windChillCelsius = 0.0,
        willChillFahrenheit = 0.0,
        windDirection = "",
        windDegree = 0,
        windMph = 0.0,
        gustKph = 0.0,
        gustMph = 0.0,
        cloud = 0,
        dewPointCelsius = 0.0,
        dewPointFahrenheit = 0.0,
        headIndexCelsius = 0.0,
        heatIndexFahrenheit = 0.0,
        willItRain = 0,
        chanceOfRain = 0,
        precipitationInches = 0.0,
        willItSnow = 0,
        chanceOfSnow = 0,
        pressureMb = 0.0,
        pressureIn = 0.0,
        visibilityMiles = 0.0,
    )

    private val todayForecast1 = RepoForecastDay(
        day = Day(
            condition = Condition(
                code = IconCodes[0],
                icon = "",
                text = WeatherDescriptions[0],
            ),
            averageTempCelsius = 0.0,
            averageTempFahrenheit = 0.0,
            maxTempCelsius = 0.0,
            maxTempFahrenheit = 0.0,
            minTempCelsius = 0.0,
            minTempFahrenheit = 0.0,
            dailyChanceOfRain = 0,
            dailyWillItRain = 0,
            totalPrecipitationMm = 0.0,
            totalPrecipitationIn = 0.0,
            dailyWillItSnow = 0,
            dailyChanceOfSnow = 0,
            averageHumidity = 0.0,
            maxWindKph = 0.0,
            maxWindMph = 0.0,
            averageVisibilityKm = 0.0,
            averageVisibilityMiles = 0.0,
            uvIndex = 0.0,
        ),
        date = Date,
        dateEpoch = DateEpoch,
        astro = Astro(
            sunrise = Sunrise,
            sunset = Sunset,
            moonrise = "",
            moonset = "",
            moonIllumination = 0.0,
            moonPhase = "",
        ),
        hour = listOf(
            forecastHour1,
            forecastHour2,
        ),
    )

    private val repoCurrent = RepoCurrent(
        isDay = 0,
        lastUpdated = "",
        lastUpdatedEpoch = 0,
        condition = Condition(
            code = 0,
            icon = "",
            text = "",
        ),
        tempCelsius = CurrentTemperature,
        tempFahrenheit = 0.0,
        feelsLikeCelsius = CurrentFeelsLike,
        feelsLikeFahrenheit = 0.0,
        uvIndex = CurrentUvIndex.toDouble(),
        precipitationMm = CurrentPrecipitation,
        precipitationInches = 0.0,
        humidity = CurrentHumidity,
        pressureMb = CurrentPressure,
        pressureIn = 0.0,
        windDirection = "",
        windDegree = 0,
        windKph = CurrentWind,
        windMph = 0.0,
        gustKph = CurrentGust,
        gustMph = 0.0,
        cloud = 0,
        visibilityKm = CurrentVisibility,
        visibilityMiles = 0.0,
    )

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

    private val forecastWeatherResponse = ForecastResponse(
        current = repoCurrent,
        location = emptyLocation,
        forecast = RepoForecast(
            forecastDay = listOf(todayForecast1),
        ),
    )

    private val successfulForecast1Entry1 = ForecastEntry(
        zonedDateTime = forecastIsoTime[0].toZonedDateTime(zoneId = UtcZoneId),
        description = WeatherDescriptions[0],
        iconCode = IconCodes[0],
        temperature = Temperature.fromCelsius(Temperatures[0]),
        feelsLike = Temperature.fromCelsius(FeelsLikeTemperatures[0]),
        precipitation = Precipitation.fromMillimeters(Precipitations[0]),
        uvIndex = UvIndex(UvIndexes[0]),
        windSpeed = Speed.fromKph(WindSpeeds[0]),
        humidity = Humidity(HumidityPercents[0]),
        visibility = AverageVisibility.fromKm(VisibilityKilometers[0]),
    )

    private val successfulForecast1Entry2 = ForecastEntry(
        zonedDateTime = forecastIsoTime[1].toZonedDateTime(zoneId = UtcZoneId),
        description = WeatherDescriptions[1],
        iconCode = IconCodes[1],
        temperature = Temperature.fromCelsius(Temperatures[1]),
        feelsLike = Temperature.fromCelsius(FeelsLikeTemperatures[1]),
        precipitation = Precipitation.fromMillimeters(Precipitations[1]),
        uvIndex = UvIndex(UvIndexes[1]),
        windSpeed = Speed.fromKph(WindSpeeds[1]),
        humidity = Humidity(HumidityPercents[1]),
        visibility = AverageVisibility.fromKm(VisibilityKilometers[1]),
    )

    private val successfulForecastDay1 =
        ForecastDay(
            date = Instant.ofEpochSecond(DateEpoch.toLong()).atZone(UtcZoneId),
            sunrise = Sunrise,
            sunset = Sunset,
            entries = listOf(
                successfulForecast1Entry1,
                successfulForecast1Entry2,
            ),
        )

    private val forecastSuccessfulResponse =
        Forecast(
            current = Current(
                description = "",
                code = 0,
                temperature = Temperature.fromCelsius(CurrentTemperature),
                feelsLike = Temperature.fromCelsius(CurrentFeelsLike),
                wind = Speed.fromKph(CurrentWind),
                gust = Speed.fromKph(CurrentGust),
                humidity = Humidity(CurrentHumidity),
                pressure = Pressure.fromMillibars(CurrentPressure),
                precipitation = Precipitation.fromMillimeters(CurrentPrecipitation),
                uvIndex = UvIndex(CurrentUvIndex),
                visibility = AverageVisibility.fromKm(CurrentVisibility),
                iconCode = 0,
            ),
            items = listOf(
                successfulForecastDay1,
            ),
        )

    private val incomingCity = WeatherLocation.City(
        name = CityName,
        countryCode = CountryCode,
    )

    private val incomingCoordinates = WeatherLocation.Coordinates(
        latitude = CityLatitude,
        longitude = CityLongitude,
    )

    @Test
    fun `interactor calls repository with incoming city arguments`() = runTest {
        val repository = FakeWeatherRepository().apply {
            forecastResponse = forecastWeatherResponse
        }

        val interactor = getInteractor(
            repository = repository,
        )
        interactor(GetForecastInteractor.Params(incomingCity))
        assertThat((repository.lastLocation as RepoWeatherLocation.City).name)
            .isEqualTo(incomingCity.name)
        assertThat((repository.lastLocation as RepoWeatherLocation.City).countryCode)
            .isEqualTo(incomingCity.countryCode)
    }

    @Test
    fun `interactor calls repository with incoming coordinate arguments`() = runTest {
        val repository = FakeWeatherRepository().apply {
            forecastResponse = forecastWeatherResponse
        }

        val interactor = getInteractor(
            repository = repository,
        )
        interactor(GetForecastInteractor.Params(incomingCoordinates))

        assertThat((repository.lastLocation as RepoWeatherLocation.Coordinates).latitude)
            .isEqualTo(incomingCoordinates.latitude)
        assertThat((repository.lastLocation as RepoWeatherLocation.Coordinates).longitude)
            .isEqualTo(incomingCoordinates.longitude)
    }

    @Test
    fun `interactor maps network data to interactor today weather`() = runTest {
        val repository = FakeWeatherRepository().apply {
            forecastResponse = forecastWeatherResponse
        }
        val interactor = getInteractor(
            repository = repository,
        )
        val response = interactor(GetForecastInteractor.Params(incomingCity))

        assertThat(response.isSuccess).isTrue()
        assertThat(response.valueOrNull()).isEqualTo(forecastSuccessfulResponse)
    }

    @Test
    fun `interactor maps network error to interactor error`() = runTest {
        val repository = FakeWeatherRepository().apply { networkError = true }
        val interactor = getInteractor(
            repository = repository,
        )
        val response = interactor(GetForecastInteractor.Params(incomingCity))

        assertThat(response.isFailure).isTrue()
        assertThat(response.throwableOrNull()).isNotNull().isInstanceOf<WeatherException>()
    }

    @Test
    fun `interactor filters out forecast items in the past`() = runTest {
        val repository = FakeWeatherRepository().apply {
            forecastResponse = forecastWeatherResponse
        }
        // set the current time to the 2nd entry's time (so we should drop the 1st one)
        val now = forecastIsoTime[1].toZonedDateTime(zoneId = UtcZoneId).toInstant()
        val timeProvider = FakeTimeProvider().apply {
            setCurrentEpoch(now)
        }
        val interactor = getInteractor(
            repository = repository,
            timeProvider = timeProvider,
        )
        val response = interactor(GetForecastInteractor.Params(incomingCity))
        assertThat(response.isSuccess).isTrue()
        val actualForecastItemsSize = requireNotNull(response.valueOrNull()).items.size
        val expectedForecastItemsSize = forecastSuccessfulResponse.items.first().entries.size - 1
        assertThat(actualForecastItemsSize).isEqualTo(expectedForecastItemsSize)
    }

    private fun getInteractor(
        repository: WeatherRepository,
        dispatcherProvider: TestDispatcherProvider = TestDispatcherProvider(),
        timeProvider: TimeProvider = this@ForecastWeatherInteractorTest.timeProvider,
        zoneIdProvider: FakeZoneIdProvider = this@ForecastWeatherInteractorTest.zoneIdProvider,
    ): GetForecastInteractor = GetForecastInteractorImpl(
        weatherRepository = repository,
        dispatcherProvider = dispatcherProvider,
        timeProvider = timeProvider,
        zoneIdProvider = zoneIdProvider,
    )
}
