package com.francescsoftware.weathersample.data.repository.weather.impl.model.forecast

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDaysModel(

    @SerialName("forecastday")
    val forecastDay: List<ForecastDayModel>? = null,
)
