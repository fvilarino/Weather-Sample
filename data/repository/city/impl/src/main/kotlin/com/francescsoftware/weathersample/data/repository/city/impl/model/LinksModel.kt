package com.francescsoftware.weathersample.data.repository.city.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class LinksModel(

    @SerialName("rel")
    val rel: String? = null,

    @SerialName("href")
    val href: String? = null
)
