package com.francescsoftware.weathersample.weatherrepository.impl.model.today

import com.francescsoftware.weathersample.weatherrepository.impl.model.CurrentModel
import com.francescsoftware.weathersample.weatherrepository.impl.model.LocationModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TodayWeatherModel(

    @SerialName("current")
    val current: CurrentModel? = null,

    @SerialName("location")
    val location: LocationModel? = null
)
