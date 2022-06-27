package com.francescsoftware.weathersample.cityrepository.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Metadata(

    @SerialName("currentOffset")
    val currentOffset: Int? = null,

    @SerialName("totalCount")
    val totalCount: Int? = null
)
