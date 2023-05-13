package com.francescsoftware.weathersample.interactor.weather.impl

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
import com.francescsoftware.weathersample.interactor.weather.api.WeatherException
import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.interactor.weather.api.model.Current
import com.francescsoftware.weathersample.interactor.weather.api.model.Forecast
import com.francescsoftware.weathersample.interactor.weather.api.model.ForecastDay
import com.francescsoftware.weathersample.interactor.weather.api.model.ForecastEntry
import com.francescsoftware.weathersample.testing.fake.dispatcher.TestDispatcherProvider
import com.francescsoftware.weathersample.testing.fake.time.FakeTimeFormatter
import com.francescsoftware.weathersample.testing.fake.time.FakeTimeParser
import com.francescsoftware.weathersample.time.api.Iso8601DateTime
import com.francescsoftware.weathersample.weatherrepository.api.model.Condition
import com.francescsoftware.weathersample.weatherrepository.api.model.Location
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.Astro
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.Day
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.ForecastHour
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.ForecastResponse
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.util.Date
import com.francescsoftware.weathersample.weatherrepository.api.WeatherLocation as RepositoryLocation
import com.francescsoftware.weathersample.weatherrepository.api.model.Current as RepoCurrent
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.Forecast as RepositoryForecast
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.ForecastDay as RepoForecastDay

private const val CityName = "Vancouver"
private const val CountryCode = "CA"
private const val CityLatitude = 49.24
private const val CityLongitude = -123.11

private const val Date = "2022-07-30"
private const val DateEpoch = 1659132000
private const val Sunrise = "06:44 AM"
private const val Sunset = "09:11 PM"

private const val currentTemperature = 17.4
private const val currentFeelsLike = 15.1
private const val currentWind = 3.4
private const val currentHumidity = 62
private const val currentPressure = 1024.0
private const val currentPrecipitation = 1.8
private const val currentUvIndex = 7
private const val currentVisibility = 10.0

private val ForecastTimes = listOf("2022-07-29 16:00", "2022-07-29 17:00")
private val Temperatures = listOf(11.5, 13.7)
private val FeelsLikeTemperatures = listOf(9.5, 11.5)
private val IconCodes = listOf(1000, 1240)
private val Precipitations = listOf(14.0, 23.3)
private val UvIndexes = listOf(3, 5)
private val HumidityPercents = listOf(54, 71)
private val VisibilityKilometers = listOf(3.4, 4.7)
private val WeatherDescriptions = listOf("sunny", "partly cloudy")
private val WindSpeeds = listOf(4.5, 3.8)

@ExperimentalCoroutinesApi
internal class ForecastWeatherInteractorTest {

    private val timeFormatter = FakeTimeFormatter()
    private val timeParser = FakeTimeParser()

    private val forecastIsoTime = ForecastTimes.map { Iso8601DateTime(it) }
    private val forecastHour1 = ForecastHour(
        isDay = 1,
        time = forecastIsoTime[0].date,
        timeEpoch = timeParser.parseDate(forecastIsoTime[0]).time.toInt(),
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
        timeEpoch = timeParser.parseDate(forecastIsoTime[1]).time.toInt(),
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
            moonIllumination = "",
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
        tempCelsius = currentTemperature,
        tempFahrenheit = 0.0,
        feelsLikeCelsius = currentFeelsLike,
        feelsLikeFahrenheit = 0.0,
        uvIndex = currentUvIndex.toDouble(),
        precipitationMm = currentPrecipitation,
        precipitationInches = 0.0,
        humidity = currentHumidity,
        pressureMb = currentPressure,
        pressureIn = 0.0,
        windDirection = "",
        windDegree = 0,
        windKph = currentWind,
        windMph = 0.0,
        gustKph = 0.0,
        gustMph = 0.0,
        cloud = 0,
        visibilityKm = currentVisibility,
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
        forecast = RepositoryForecast(
            forecastDay = listOf(todayForecast1),
        )
    )

    private val successfulForecast1Entry1 = ForecastEntry(
        date = timeParser.parseDate(forecastIsoTime[0]),
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
        date = timeParser.parseDate(forecastIsoTime[1]),
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
        current = Current(
            description = "",
            code = 0,
            temperature = Temperature.fromCelsius(currentTemperature),
            feelsLike = Temperature.fromCelsius(currentFeelsLike),
            wind = Speed.fromKph(currentWind),
            humidity = Humidity(currentHumidity),
            pressure = Pressure.fromMillibars(currentPressure),
            precipitation = Precipitation.fromMillimeters(currentPrecipitation),
            uvIndex = UvIndex(currentUvIndex),
            visibility = AverageVisibility.fromKm(currentVisibility),
            iconCode = 0,
        ),
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

    @Test
    fun `interactor calls repository with incoming city arguments`() = runTest {
        val repository = FakeWeatherRepository().apply {
            forecastResponse = forecastWeatherResponse
        }

        val interactor = GetForecastInteractorImpl(
            repository,
            TestDispatcherProvider(),
            timeFormatter,
            timeParser,
        )
        interactor(incomingCity)
        Truth.assertThat((repository.lastLocation as RepositoryLocation.City).name).isEqualTo(incomingCity.name)
        Truth
            .assertThat((repository.lastLocation as RepositoryLocation.City).countryCode)
            .isEqualTo(incomingCity.countryCode)
    }

    @Test
    fun `interactor calls repository with incoming coordinate arguments`() = runTest {
        val repository = FakeWeatherRepository().apply {
            forecastResponse = forecastWeatherResponse
        }

        val interactor = GetForecastInteractorImpl(
            repository,
            TestDispatcherProvider(),
            timeFormatter,
            timeParser,
        )

        interactor(incomingCoordinates)

        Truth
            .assertThat((repository.lastLocation as RepositoryLocation.Coordinates).latitude)
            .isEqualTo(incomingCoordinates.latitude)
        Truth
            .assertThat((repository.lastLocation as RepositoryLocation.Coordinates).longitude)
            .isEqualTo(incomingCoordinates.longitude)
    }

    @Test
    fun `interactor maps network data to interactor today weather`() = runTest {
        val repository = FakeWeatherRepository().apply {
            forecastResponse = forecastWeatherResponse
        }
        val interactor = GetForecastInteractorImpl(
            repository,
            TestDispatcherProvider(),
            timeFormatter,
            timeParser,
        )
        val response = interactor(incomingCity)

        Truth.assertThat(response.isSuccess).isTrue()
        Truth.assertThat(response.valueOrNull()).isEqualTo(forecastSuccessfulResponse)
    }

    @Test
    fun `interactor maps network error to interactor error`() = runTest {
        val repository = FakeWeatherRepository().apply { networkError = true }
        val interactor = GetForecastInteractorImpl(
            repository,
            TestDispatcherProvider(),
            timeFormatter,
            timeParser,
        )

        val response = interactor(incomingCity)

        Truth.assertThat(response.isFailure).isTrue()
        Truth.assertThat(response.throwableOrNull()).isInstanceOf(WeatherException::class.java)
    }
}
