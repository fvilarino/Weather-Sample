package com.francescsoftware.weathersample.repository.weather.model.forecast

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(

    @SerialName("city")
    val city: City? = null,

    @SerialName("cnt")
    val cnt: Int? = null,

    @SerialName("cod")
    val cod: String? = null,

    @SerialName("message")
    val message: Int? = null,

    @SerialName("list")
    val forecast: List<ForecastItem>? = null
)

