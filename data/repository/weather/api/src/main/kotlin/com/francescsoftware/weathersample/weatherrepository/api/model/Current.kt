package com.francescsoftware.weathersample.weatherrepository.api.model

/**
 * Current weather
 *
 * @property isDay
 * @property lastUpdated when this data was last refreshed
 * @property lastUpdatedEpoch when this date was last refreshed, as seconds from Unix epoch
 * @property tempCelsius temperature in Celsius
 * @property tempFahrenheit temperature in Fahrenheit
 * @property feelsLikeCelsius feels like temperature in Celsius
 * @property feelsLikeFahrenheit feels like temperature in Fahrenheit
 * @property humidity humidyt
 * @property uvIndex UV index
 * @property windKph wind speed in km/h
 * @property windMph wind speed in mi/h
 * @property windDegree wind degrees (0-360)
 * @property windDirection wind direction
 * @property gustKph gust speed in km/h
 * @property gustMph gust speed in mi/h
 * @property precipitationMm precipitation in mm
 * @property precipitationInches precipitation in inches
 * @property cloud cloud cover
 * @property pressureMb atmospheric pressure in millibars
 * @property pressureIn atmospheric pressure in inches
 * @property condition weather condition
 * @property visibilityKm visibility in kilometers
 * @property visibilityMiles visibility in miles
 */
data class Current(
    val isDay: Int,
    val lastUpdated: String,
    val lastUpdatedEpoch: Int,
    val tempCelsius: Double,
    val tempFahrenheit: Double,
    val feelsLikeCelsius: Double,
    val feelsLikeFahrenheit: Double,
    val humidity: Int,
    val uvIndex: Double,
    val windKph: Double,
    val windMph: Double,
    val windDegree: Int,
    val windDirection: String,
    val gustKph: Double,
    val gustMph: Double,
    val precipitationMm: Double,
    val precipitationInches: Double,
    val cloud: Int,
    val pressureMb: Double,
    val pressureIn: Double,
    val condition: Condition,
    val visibilityKm: Double,
    val visibilityMiles: Double,
)
