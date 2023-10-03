package com.francescsoftware.weathersample.data.repository.weather.impl.model.forecast

import com.francescsoftware.weathersample.data.repository.weather.impl.model.CurrentModel
import com.francescsoftware.weathersample.data.repository.weather.impl.model.LocationModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastModel(

    @SerialName("current")
    val current: CurrentModel? = null,

    @SerialName("location")
    val location: LocationModel? = null,

    @SerialName("forecast")
    val forecast: ForecastDaysModel? = null,
)
