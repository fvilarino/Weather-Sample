package com.francescsoftware.weathersample.cityrepository.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CitySearchResponse(

    @SerialName("metadata")
    val metadata: Metadata? = null,

    @SerialName("data")
    val data: List<CityItem>? = null,

    @SerialName("links")
    val links: List<LinksItem>? = null
)
