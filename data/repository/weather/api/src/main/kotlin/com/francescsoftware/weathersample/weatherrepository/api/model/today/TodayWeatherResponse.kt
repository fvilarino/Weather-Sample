package com.francescsoftware.weathersample.weatherrepository.api.model.today

import com.francescsoftware.weathersample.weatherrepository.api.model.Current
import com.francescsoftware.weathersample.weatherrepository.api.model.Location
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodayWeatherResponse(

    @SerialName("current")
    val current: Current? = null,

    @SerialName("location")
    val location: Location? = null
)
