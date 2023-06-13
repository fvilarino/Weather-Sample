package com.francescsoftware.weathersample.data.repository.weather.impl.model.forecast

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AstroModel(

    @SerialName("moonset")
    val moonset: String? = null,

    @SerialName("moon_illumination")
    val moonIllumination: String? = null,

    @SerialName("sunrise")
    val sunrise: String? = null,

    @SerialName("moon_phase")
    val moonPhase: String? = null,

    @SerialName("sunset")
    val sunset: String? = null,

    @SerialName("moonrise")
    val moonrise: String? = null,
)
