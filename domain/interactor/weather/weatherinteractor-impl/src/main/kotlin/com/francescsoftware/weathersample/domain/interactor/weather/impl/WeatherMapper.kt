package com.francescsoftware.weathersample.domain.interactor.weather.impl

import com.francescsoftware.weathersample.core.time.api.Iso8601DateTime
import com.francescsoftware.weathersample.core.type.weather.AverageVisibility
import com.francescsoftware.weathersample.core.type.weather.Humidity
import com.francescsoftware.weathersample.core.type.weather.Precipitation
import com.francescsoftware.weathersample.core.type.weather.Pressure
import com.francescsoftware.weathersample.core.type.weather.Speed
import com.francescsoftware.weathersample.core.type.weather.Temperature
import com.francescsoftware.weathersample.core.type.weather.UvIndex
import com.francescsoftware.weathersample.data.repository.weather.api.model.forecast.ForecastHour
import com.francescsoftware.weathersample.data.repository.weather.api.model.today.TodayWeatherResponse
import com.francescsoftware.weathersample.domain.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.Current
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.ForecastEntry
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.TodayClouds
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.TodayMain
import com.francescsoftware.weathersample.domain.interactor.weather.api.model.TodayWind
import java.time.ZoneId
import kotlin.math.roundToInt
import com.francescsoftware.weathersample.data.repository.weather.api.WeatherLocation as RepositoryLocation
import com.francescsoftware.weathersample.data.repository.weather.api.model.Current as RepoCurrent

internal fun WeatherLocation.toRepositoryLocation() = when (this) {
    is WeatherLocation.City -> RepositoryLocation.City(
        name = name,
        countryCode = countryCode,
    )

    is WeatherLocation.Location -> RepositoryLocation.Location(
        coordinates = coordinates,
    )
}

internal fun TodayWeatherResponse.toTodayMain(): TodayMain =
    TodayMain(
        code = current.condition.code,
        description = current.condition.text,
        temperature = Temperature.fromCelsius(current.tempCelsius),
        feelsLike = Temperature.fromCelsius(current.feelsLikeCelsius),
        humidity = Humidity(current.humidity),
        pressure = Pressure.fromMillibars(current.pressureMb),
        precipitation = Precipitation.fromMillimeters(current.precipitationMm),
        uvIndex = UvIndex(current.uvIndex.roundToInt()),
    )

internal fun TodayWeatherResponse.toTodayWind(): TodayWind =
    TodayWind(
        direction = current.windDirection,
        speed = Speed.fromKph(current.windKph),
        gust = Speed.fromKph(current.gustKph),
    )

internal fun TodayWeatherResponse.toTodayClouds(): TodayClouds =
    TodayClouds(
        all = current.cloud,
    )

internal fun RepoCurrent.toCurrent(): Current = Current(
    description = condition.text,
    code = condition.code,
    temperature = Temperature.fromCelsius(tempCelsius),
    feelsLike = Temperature.fromCelsius(feelsLikeCelsius),
    wind = Speed.fromKph(windKph),
    gust = Speed.fromKph(gustKph),
    humidity = Humidity(humidity),
    pressure = Pressure.fromMillibars(pressureMb),
    precipitation = Precipitation.fromMillimeters(precipitationMm),
    uvIndex = UvIndex(uvIndex.roundToInt()),
    visibility = AverageVisibility.fromKm(visibilityKm),
    iconCode = condition.code,
)

internal fun ForecastHour.toForecastEntry(zoneId: ZoneId) = ForecastEntry(
    zonedDateTime = this.time.let { date -> Iso8601DateTime(date).toZonedDateTime(zoneId = zoneId) },
    description = condition.text,
    iconCode = condition.code,
    temperature = Temperature.fromCelsius(tempCelsius),
    feelsLike = Temperature.fromCelsius(feelsLikeCelsius),
    precipitation = Precipitation.fromMillimeters(precipitationMm),
    windSpeed = Speed.fromKph(windKph),
    uvIndex = UvIndex(uvIndex.roundToInt()),
    humidity = Humidity(humidity),
    visibility = AverageVisibility.fromKm(visibilityKm),
)
