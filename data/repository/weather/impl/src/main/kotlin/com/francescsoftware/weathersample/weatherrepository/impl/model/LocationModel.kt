package com.francescsoftware.weathersample.weatherrepository.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class LocationModel(

    @SerialName("localtime")
    val localtime: String? = null,

    @SerialName("country")
    val country: String? = null,

    @SerialName("localtime_epoch")
    val localtimeEpoch: Int? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("lon")
    val lon: Double? = null,

    @SerialName("region")
    val region: String? = null,

    @SerialName("lat")
    val lat: Double? = null,

    @SerialName("tz_id")
    val tzId: String? = null,
)
