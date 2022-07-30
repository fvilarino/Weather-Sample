package com.francescsoftware.weathersample.weatherrepository.api.model.forecast

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Astro(

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
