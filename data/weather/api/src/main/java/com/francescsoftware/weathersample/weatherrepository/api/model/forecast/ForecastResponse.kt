package com.francescsoftware.weathersample.weatherrepository.api.model.forecast

import com.francescsoftware.weathersample.weatherrepository.api.model.forecast.ForecastItem
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

