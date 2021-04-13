package com.francescsoftware.weathersample.repository.weather.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Clouds(

    @SerialName("all")
    val all: Int? = null
)
