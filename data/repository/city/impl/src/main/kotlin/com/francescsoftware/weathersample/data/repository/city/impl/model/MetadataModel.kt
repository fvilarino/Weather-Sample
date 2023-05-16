package com.francescsoftware.weathersample.data.repository.city.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MetadataModel(

    @SerialName("currentOffset")
    val currentOffset: Int? = null,

    @SerialName("totalCount")
    val totalCount: Int? = null
)
