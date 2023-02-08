package com.francescsoftware.weathersample.weatherrepository.api.model.forecast

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Forecast(

    @SerialName("forecastday")
    val forecastDay: List<ForecastDayItem>? = null,
)
