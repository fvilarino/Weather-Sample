package com.francescsoftware.weathersample.presentation.feature.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SelectedCity(
    @SerialName("name")
    val name: String,
    @SerialName("country")
    val country: String,
    @SerialName("countryCode")
    val countryCode: String,
)
