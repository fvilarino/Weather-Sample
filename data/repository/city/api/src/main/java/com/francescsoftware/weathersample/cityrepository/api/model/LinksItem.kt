 package com.francescsoftware.weathersample.cityrepository.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinksItem(

    @SerialName("rel")
    val rel: String? = null,

    @SerialName("href")
    val href: String? = null
)
