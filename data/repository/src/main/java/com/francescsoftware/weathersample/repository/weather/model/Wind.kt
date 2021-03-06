package com.francescsoftware.weathersample.repository.weather.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Wind(

    @SerialName("deg")
    val deg: Int? = null,

    @SerialName("speed")
    val speed: Double? = null,

    @SerialName("gust")
    val gust: Double? = null
)
