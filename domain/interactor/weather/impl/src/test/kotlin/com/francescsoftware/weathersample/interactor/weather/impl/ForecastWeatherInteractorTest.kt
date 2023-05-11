package com.francescsoftware.weathersample.interactor.weather.impl

import com.francescsoftware.weathersample.testing.fake.dispatcher.TestDispatcherProvider
import com.francescsoftware.weathersample.interactor.weather.api.model.Forecast
import com.francescsoftware.weathersample.interactor.weather.api.model.ForecastDay
import com.francescsoftware.weathersample.interactor.weather.api.model.ForecastEntry
import com.francescsoftware.weathersample.interactor.weather.api.WeatherException
import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.time.api.Iso8601DateTime
import com.francescsoftware.weathersample.testing.fake.time.FakeTimeFormatter
import com.francescsoftware.weathersample.testing.fake.time.FakeTimeParser
import com.francescsoftware.weathersample.type.isFailure
import com.francescsoftware.weathersample.type.isSuccess
import com.francescsoftware.weathersample.type.throwableOrNull
import com.francescsoftware.weathersample.type.valueOrNull
import com.francescsoftware.weathersample.weatherrepository.api.model.Condition
import com.francescsoftware.weathersample.weatherrepository.api.model.Current
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

    private val timeFormatter = FakeTimeFormatter()
    private val timeParser = FakeTimeParser()

    private val forecastIsoTime = ForecastTime.map { Iso8601DateTime(it) }
    private val forecastHour1 = ForecastHour(
        isDay = 1,
        time = forecastIsoTime[0].date,
        timeEpoch = timeParser.parseDate(forecastIsoTime[0]).time.toInt(),
        tempCelsius = Temperature[0],
        tempFahrenheit = 0.0,
        feelsLikeCelsius = FeelsLikeTemperature[0],
        feelsLikeFahrenheit = 0.0,
        windChillCelsius = 0.0,
        willChillFahrenheit = 0.0,
        windDirection = "",
        windDegree = 0,
        windKph = WindSpeed[0],
        windMph = 0.0,
        gustKph = 0.0,
        gustMph = 0.0,
        cloud = 0,
        humidity = HumidityPercent[0],
        dewPointCelsius = 0.0,
        dewPointFahrenheit = 0.0,
        uvIndex = UvIndex[0].toDouble(),
        headIndexCelsius = 0.0,
        heatIndexFahrenheit = 0.0,
        willItRain = 0,
        chanceOfRain = 0,
        precipitationMm = Precipitation[0].toDouble(),
        precipitationInches = 0.0,
        condition = Condition(
            code = IconCode[0],
            icon = "",
            text = WeatherDescription[0],
        ),
        willItSnow = 0,
        chanceOfSnow = 0,
        pressureMb = 0.0,
        pressureIn = 0.0,
        visibilityKm = VisibilityKilometers[0].toDouble(),
        visibilityMiles = 0.0,
    )

    private val forecastHour2 = ForecastHour(
        isDay = 1,
        time = forecastIsoTime[1].date,
        timeEpoch = timeParser.parseDate(forecastIsoTime[1]).time.toInt(),
        tempCelsius = Temperature[1],
        feelsLikeCelsius = FeelsLikeTemperature[1],
        precipitationMm = Precipitation[1].toDouble(),
        windKph = WindSpeed[1],
        humidity = HumidityPercent[1],
        visibilityKm = VisibilityKilometers[1].toDouble(),
        uvIndex = UvIndex[1].toDouble(),
        condition = Condition(
            code = IconCode[1],
            icon = "",
            text = WeatherDescription[1],
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
                code = IconCode[0],
                icon = "",
                text = WeatherDescription[0],
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

    private val emptyCurrent = Current(
        isDay = 0,
        lastUpdated = "",
        lastUpdatedEpoch = 0,
        condition = Condition(
            code = 0,
            icon = "",
            text = "",
        ),
        tempCelsius = 0.0,
        tempFahrenheit = 0.0,
        feelsLikeCelsius = 0.0,
        feelsLikeFahrenheit = 0.0,
        uvIndex = 0.0,
        precipitationMm = 0.0,
        precipitationInches = 0.0,
        humidity = 0,
        pressureMb = 0.0,
        pressureIn = 0.0,
        windDirection = "",
        windDegree = 0,
        windKph = 0.0,
        windMph = 0.0,
        gustKph = 0.0,
        gustMph = 0.0,
        cloud = 0,
        visibilityKm = 0.0,
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
        current = emptyCurrent,
        location = emptyLocation,
        forecast = RepositoryForecast(
            forecastDay = listOf(todayForecast1),
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
        interactor.execute(incomingCity)
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

        interactor.execute(incomingCoordinates)

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
        val response = interactor.execute(incomingCity)

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

        val response = interactor.execute(incomingCity)

        Truth.assertThat(response.isFailure).isTrue()
        Truth.assertThat(response.throwableOrNull()).isInstanceOf(WeatherException::class.java)
    }
}
