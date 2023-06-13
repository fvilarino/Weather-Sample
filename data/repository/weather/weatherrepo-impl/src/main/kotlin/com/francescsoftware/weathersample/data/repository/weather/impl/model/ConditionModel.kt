package com.francescsoftware.weathersample.data.repository.weather.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ConditionModel(

    @SerialName("code")
    val code: Int? = null,

    @SerialName("icon")
    val icon: String? = null,

    @SerialName("text")
    val text: String? = null,
)
