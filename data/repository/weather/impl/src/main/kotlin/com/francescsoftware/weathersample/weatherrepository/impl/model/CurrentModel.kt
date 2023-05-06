package com.francescsoftware.weathersample.weatherrepository.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CurrentModel(

    @SerialName("feelslike_c")
    val feelslikeC: Double? = null,

    @SerialName("uv")
    val uv: Double? = null,

    @SerialName("last_updated")
    val lastUpdated: String? = null,

    @SerialName("feelslike_f")
    val feelslikeF: Double? = null,

    @SerialName("wind_degree")
    val windDegree: Int? = null,

    @SerialName("last_updated_epoch")
    val lastUpdatedEpoch: Int? = null,

    @SerialName("is_day")
    val isDay: Int? = null,

    @SerialName("precip_in")
    val precipIn: Double? = null,

    @SerialName("wind_dir")
    val windDir: String? = null,

    @SerialName("gust_mph")
    val gustMph: Double? = null,

    @SerialName("temp_c")
    val tempC: Double? = null,

    @SerialName("pressure_in")
    val pressureIn: Double? = null,

    @SerialName("gust_kph")
    val gustKph: Double? = null,

    @SerialName("temp_f")
    val tempF: Double? = null,

    @SerialName("precip_mm")
    val precipMm: Double? = null,

    @SerialName("cloud")
    val cloud: Int? = null,

    @SerialName("wind_kph")
    val windKph: Double? = null,

    @SerialName("condition")
    val condition: ConditionModel? = null,

    @SerialName("wind_mph")
    val windMph: Double? = null,

    @SerialName("vis_km")
    val visKm: Double? = null,

    @SerialName("humidity")
    val humidity: Int? = null,

    @SerialName("pressure_mb")
    val pressureMb: Double? = null,

    @SerialName("vis_miles")
    val visMiles: Double? = null,
)
