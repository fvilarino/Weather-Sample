package com.francescsoftware.weathersample.data.repository.city.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinksModel(

    @SerialName("rel")
    val rel: String? = null,

    @SerialName("href")
    val href: String? = null,
)
