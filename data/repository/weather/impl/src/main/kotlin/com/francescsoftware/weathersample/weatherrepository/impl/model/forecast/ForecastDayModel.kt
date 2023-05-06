package com.francescsoftware.weathersample.weatherrepository.impl.model.forecast

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ForecastDayModel(

    @SerialName("date")
    val date: String? = null,

    @SerialName("date_epoch")
    val dateEpoch: Int? = null,

    @SerialName("astro")
    val astro: AstroModel? = null,

    @SerialName("day")
    val day: DayModel? = null,

    @SerialName("hour")
    val hour: List<HourModel>? = null,
)
