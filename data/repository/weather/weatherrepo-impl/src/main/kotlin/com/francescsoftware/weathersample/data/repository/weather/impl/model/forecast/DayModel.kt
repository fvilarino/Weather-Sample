package com.francescsoftware.weathersample.data.repository.weather.impl.model.forecast

import com.francescsoftware.weathersample.data.repository.weather.impl.model.ConditionModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class DayModel(

    @SerialName("avgvis_km")
    val avgvisKm: Double? = null,

    @SerialName("uv")
    val uv: Double? = null,

    @SerialName("avgtemp_f")
    val avgtempF: Double? = null,

    @SerialName("avgtemp_c")
    val avgtempC: Double? = null,

    @SerialName("daily_chance_of_snow")
    val dailyChanceOfSnow: Int? = null,

    @SerialName("maxtemp_c")
    val maxtempC: Double? = null,

    @SerialName("maxtemp_f")
    val maxtempF: Double? = null,

    @SerialName("mintemp_c")
    val mintempC: Double? = null,

    @SerialName("avgvis_miles")
    val avgvisMiles: Double? = null,

    @SerialName("daily_will_it_rain")
    val dailyWillItRain: Int? = null,

    @SerialName("mintemp_f")
    val mintempF: Double? = null,

    @SerialName("totalprecip_in")
    val totalprecipIn: Double? = null,

    @SerialName("avghumidity")
    val avghumidity: Double? = null,

    @SerialName("condition")
    val condition: ConditionModel? = null,

    @SerialName("maxwind_kph")
    val maxwindKph: Double? = null,

    @SerialName("maxwind_mph")
    val maxwindMph: Double? = null,

    @SerialName("daily_chance_of_rain")
    val dailyChanceOfRain: Int? = null,

    @SerialName("totalprecip_mm")
    val totalprecipMm: Double? = null,

    @SerialName("daily_will_it_snow")
    val dailyWillItSnow: Int? = null,
)
