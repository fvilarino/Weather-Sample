package com.francescsoftware.weathersample.repository.weather.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Snow(

    @SerialName("3h")
    val threeHours: Double? = null
)
