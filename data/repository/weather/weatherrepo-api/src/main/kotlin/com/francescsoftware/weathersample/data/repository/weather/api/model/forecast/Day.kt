package com.francescsoftware.weathersample.data.repository.weather.api.model.forecast

import com.francescsoftware.weathersample.data.repository.weather.api.model.Condition

/**
 * Day weather info
 *
 * @property condition weather condition
 * @property averageTempCelsius average temperature in Celsius
 * @property averageTempFahrenheit average temperature in Fahrenheit
 * @property maxTempCelsius max temperature in Celsius
 * @property maxTempFahrenheit max temperature in Fahrenheit
 * @property minTempCelsius min temperature in Celsius
 * @property minTempFahrenheit min temperature in Fahrenheit
 * @property dailyChanceOfRain daily chance of rain
 * @property dailyWillItRain whether it may rain
 * @property totalPrecipitationMm total precipitation in millimeters
 * @property totalPrecipitationIn total precipitation in inches
 * @property dailyWillItSnow whether it may snow
 * @property dailyChanceOfSnow chance of snow
 * @property averageHumidity average humidity
 * @property maxWindKph maximum wind speed in km/h
 * @property maxWindMph maximum wind speed in mi/h
 * @property averageVisibilityKm average visibility in km
 * @property averageVisibilityMiles average visibility in miles
 * @property uvIndex UV index
 */
data class Day(
    val condition: Condition,
    val averageTempCelsius: Double,
    val averageTempFahrenheit: Double,
    val maxTempCelsius: Double,
    val maxTempFahrenheit: Double,
    val minTempCelsius: Double,
    val minTempFahrenheit: Double,
    val dailyChanceOfRain: Int,
    val dailyWillItRain: Int,
    val totalPrecipitationMm: Double,
    val totalPrecipitationIn: Double,
    val dailyWillItSnow: Int,
    val dailyChanceOfSnow: Int,
    val averageHumidity: Double,
    val maxWindKph: Double,
    val maxWindMph: Double,
    val averageVisibilityKm: Double,
    val averageVisibilityMiles: Double,
    val uvIndex: Double,
)
