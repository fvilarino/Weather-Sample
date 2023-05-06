package com.francescsoftware.weathersample.cityrepository.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CitySearchResponseModel(

    @SerialName("metadata")
    val metadata: MetadataModel? = null,

    @SerialName("data")
    val data: List<CityModel>? = null,

    @SerialName("links")
    val links: List<LinksModel>? = null
)
