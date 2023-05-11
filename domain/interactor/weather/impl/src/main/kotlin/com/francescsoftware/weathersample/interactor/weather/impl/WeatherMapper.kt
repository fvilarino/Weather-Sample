package com.francescsoftware.weathersample.interactor.weather.impl

import com.francescsoftware.weathersample.interactor.weather.api.WeatherLocation
import com.francescsoftware.weathersample.interactor.weather.api.model.Current
import com.francescsoftware.weathersample.interactor.weather.api.model.ForecastEntry
import com.francescsoftware.weathersample.interactor.weather.api.model.TodayClouds
import com.francescsoftware.weathersample.interactor.weather.api.model.TodayMain
import com.francescsoftware.weathersample.interactor.weather.api.model.TodayWind
import com.francescsoftware.weathersample.time.api.Iso8601DateTime
import com.francescsoftware.weathersample.time.api.TimeParser
import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.ForecastHour
import com.francescsoftware.weathersample.weatherrepository.api.model.today.TodayWeatherResponse
import kotlin.math.roundToInt
import com.francescsoftware.weathersample.weatherrepository.api.WeatherLocation as RepositoryLocation
import com.francescsoftware.weathersample.weatherrepository.api.model.Current as RepoCurrent

internal fun WeatherLocation.toRepositoryLocation() = when (this) {
    is WeatherLocation.City -> RepositoryLocation.City(
        name = name,
        countryCode = countryCode,
    )

    is WeatherLocation.Coordinates -> RepositoryLocation.Coordinates(
        latitude = latitude,
        longitude = longitude,
    )
}

internal fun TodayWeatherResponse.toTodayMain(): TodayMain =
    TodayMain(
        code = current.condition.code,
        description = current.condition.text,
        temperature = current.tempCelsius,
        feelsLike = current.feelsLikeCelsius,
        humidity = current.humidity,
        pressure = current.pressureMb.roundToInt(),
        precipitation = current.precipitationMm.roundToInt(),
        uvIndex = current.uvIndex.roundToInt(),
    )

internal fun TodayWeatherResponse.toTodayWind(): TodayWind =
    TodayWind(
        direction = current.windDirection,
        speed = current.windKph,
        gust = current.gustKph,
    )

internal fun TodayWeatherResponse.toTodayClouds(): TodayClouds =
    TodayClouds(
        all = current.cloud,
    )

internal fun RepoCurrent.toCurrent(): Current = Current(
    description = condition.text,
    code = condition.code,
    temperature = tempCelsius,
    feelsLike = feelsLikeCelsius,
    humidity = humidity,
    pressure = pressureMb,
    precipitation = precipitationMm,
)

internal fun ForecastHour.toForecastEntry(
    timerParser: TimeParser,
) = ForecastEntry(
    date = this.time.let { date -> timerParser.parseDate(Iso8601DateTime(date)) },
    description = condition.text,
    iconCode = condition.code,
    temperature = tempCelsius,
    feelsLikeTemperature = feelsLikeCelsius,
    precipitation = precipitationMm.roundToInt(),
    windSpeed = windKph,
    uvIndex = uvIndex.roundToInt(),
    humidityPercent = humidity,
    visibility = visibilityKm.roundToInt(),
)
