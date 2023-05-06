package com.francescsoftware.weathersample.weatherrepository.impl.model.forecast

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ForecastDaysModel(

    @SerialName("forecastday")
    val forecastDay: List<ForecastDayModel>? = null,
)
