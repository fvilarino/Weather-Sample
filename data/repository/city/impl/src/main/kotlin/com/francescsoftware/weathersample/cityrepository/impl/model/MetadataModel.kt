package com.francescsoftware.weathersample.cityrepository.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MetadataModel(

    @SerialName("currentOffset")
    val currentOffset: Int? = null,

    @SerialName("totalCount")
    val totalCount: Int? = null
)
