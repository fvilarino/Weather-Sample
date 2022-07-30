package com.francescsoftware.weathersample.weatherrepository.api.model.forecast

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDayItem(

    @SerialName("date")
    val date: String? = null,

    @SerialName("date_epoch")
    val dateEpoch: Int? = null,

    @SerialName("astro")
    val astro: Astro? = null,

    @SerialName("day")
    val day: Day? = null,

    @SerialName("hour")
    val hour: List<HourItem>? = null,
)
