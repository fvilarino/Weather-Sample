package com.francescsoftware.weathersample.data.repository.weather.api.model

/**
 * Weather location
 *
 * @property localtime local time
 * @property localtimeEpoch local time as seconds since Unix epoch
 * @property name location name
 * @property region location region
 * @property country location country
 * @property latitude location latitude
 * @property longitude location longitude
 * @property timezoneId location timezone ID
 */
data class Location(
    val localtime: String,
    val localtimeEpoch: Int,
    val name: String,
    val region: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val timezoneId: String,
)
