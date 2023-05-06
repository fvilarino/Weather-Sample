package com.francescsoftware.weathersample.weatherrepository.impl.model.forecast

import com.francescsoftware.weathersample.weatherrepository.impl.model.ConditionModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class HourModel(

    @SerialName("feelslike_c")
    val feelslikeC: Double? = null,

    @SerialName("feelslike_f")
    val feelslikeF: Double? = null,

    @SerialName("wind_degree")
    val windDegree: Int? = null,

    @SerialName("windchill_f")
    val windchillF: Double? = null,

    @SerialName("windchill_c")
    val windchillC: Double? = null,

    @SerialName("temp_c")
    val tempC: Double? = null,

    @SerialName("temp_f")
    val tempF: Double? = null,

    @SerialName("cloud")
    val cloud: Int? = null,

    @SerialName("wind_kph")
    val windKph: Double? = null,

    @SerialName("wind_mph")
    val windMph: Double? = null,

    @SerialName("humidity")
    val humidity: Int? = null,

    @SerialName("dewpoint_f")
    val dewpointF: Double? = null,

    @SerialName("will_it_rain")
    val willItRain: Int? = null,

    @SerialName("uv")
    val uv: Double? = null,

    @SerialName("heatindex_f")
    val heatindexF: Double? = null,

    @SerialName("dewpoint_c")
    val dewpointC: Double? = null,

    @SerialName("is_day")
    val isDay: Int? = null,

    @SerialName("precip_in")
    val precipIn: Double? = null,

    @SerialName("heatindex_c")
    val heatindexC: Double? = null,

    @SerialName("wind_dir")
    val windDir: String? = null,

    @SerialName("gust_mph")
    val gustMph: Double? = null,

    @SerialName("pressure_in")
    val pressureIn: Double? = null,

    @SerialName("chance_of_rain")
    val chanceOfRain: Int? = null,

    @SerialName("gust_kph")
    val gustKph: Double? = null,

    @SerialName("precip_mm")
    val precipMm: Double? = null,

    @SerialName("condition")
    val condition: ConditionModel? = null,

    @SerialName("will_it_snow")
    val willItSnow: Int? = null,

    @SerialName("vis_km")
    val visKm: Double? = null,

    @SerialName("time_epoch")
    val timeEpoch: Int? = null,

    @SerialName("time")
    val time: String? = null,

    @SerialName("chance_of_snow")
    val chanceOfSnow: Int? = null,

    @SerialName("pressure_mb")
    val pressureMb: Double? = null,

    @SerialName("vis_miles")
    val visMiles: Double? = null,
)
