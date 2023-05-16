package com.francescsoftware.weathersample.data.repository.weather.api.model.forecast

import com.francescsoftware.weathersample.data.repository.weather.api.model.Condition

/**
 * Weather forecast for 1 hour
 *
 * @property isDay
 * @property time
 * @property timeEpoch
 * @property tempCelsius
 * @property tempFahrenheit
 * @property feelsLikeCelsius
 * @property feelsLikeFahrenheit
 * @property windChillCelsius
 * @property willChillFahrenheit
 * @property windDirection
 * @property windDegree
 * @property windKph
 * @property windMph
 * @property gustKph
 * @property gustMph
 * @property cloud
 * @property humidity
 * @property dewPointCelsius
 * @property dewPointFahrenheit
 * @property uvIndex
 * @property headIndexCelsius
 * @property heatIndexFahrenheit
 * @property willItRain
 * @property chanceOfRain
 * @property precipitationMm
 * @property precipitationInches
 * @property condition
 * @property willItSnow
 * @property chanceOfSnow
 * @property pressureMb
 * @property pressureIn
 * @property visibilityKm
 * @property visibilityMiles
 */
data class ForecastHour(
    val isDay: Int,
    val time: String,
    val timeEpoch: Int,
    val tempCelsius: Double,
    val tempFahrenheit: Double,
    val feelsLikeCelsius: Double,
    val feelsLikeFahrenheit: Double,
    val windChillCelsius: Double,
    val willChillFahrenheit: Double,
    val windDirection: String,
    val windDegree: Int,
    val windKph: Double,
    val windMph: Double,
    val gustKph: Double,
    val gustMph: Double,
    val cloud: Int,
    val humidity: Int,
    val dewPointCelsius: Double,
    val dewPointFahrenheit: Double,
    val uvIndex: Double,
    val headIndexCelsius: Double,
    val heatIndexFahrenheit: Double,
    val willItRain: Int,
    val chanceOfRain: Int,
    val precipitationMm: Double,
    val precipitationInches: Double,
    val condition: Condition,
    val willItSnow: Int,
    val chanceOfSnow: Int,
    val pressureMb: Double,
    val pressureIn: Double,
    val visibilityKm: Double,
    val visibilityMiles: Double,
)
