package com.francescsoftware.weathersample.weatherrepository.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Coord(

    @SerialName("lon")
    val lon: Double? = null,

    @SerialName("lat")
    val lat: Double? = null
)
