package com.francescsoftware.weathersample.data.repository.city.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CitySearchResponseModel(

    @SerialName("metadata")
    val metadata: MetadataModel? = null,

    @SerialName("data")
    val data: List<CityModel>? = null,

    @SerialName("links")
    val links: List<LinksModel>? = null,
)
