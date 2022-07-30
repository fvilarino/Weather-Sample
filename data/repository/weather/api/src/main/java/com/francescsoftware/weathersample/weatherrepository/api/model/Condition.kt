package com.francescsoftware.weathersample.weatherrepository.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Condition(

    @SerialName("code")
    val code: Int? = null,

    @SerialName("icon")
    val icon: String? = null,

    @SerialName("text")
    val text: String? = null,
)
