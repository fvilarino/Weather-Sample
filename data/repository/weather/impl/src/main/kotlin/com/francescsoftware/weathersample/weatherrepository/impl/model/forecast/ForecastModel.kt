package com.francescsoftware.weathersample.weatherrepository.impl.model.forecast

import com.francescsoftware.weathersample.weatherrepository.impl.model.CurrentModel
import com.francescsoftware.weathersample.weatherrepository.impl.model.LocationModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ForecastModel(

    @SerialName("current")
    val current: CurrentModel? = null,

    @SerialName("location")
    val location: LocationModel? = null,

    @SerialName("forecast")
    val forecast: ForecastDaysModel? = null
)
