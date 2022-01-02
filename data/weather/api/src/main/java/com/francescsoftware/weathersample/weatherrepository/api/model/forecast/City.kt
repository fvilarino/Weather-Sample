package com.francescsoftware.weathersample.weatherrepository.api.model.forecast

import com.francescsoftware.weathersample.weatherrepository.api.model.Coord
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class City(

    @SerialName("country")
    val country: String? = null,

    @SerialName("coord")
    val coord: Coord? = null,

    @SerialName("sunrise")
    val sunrise: Int? = null,

    @SerialName("timezone")
    val timezone: Int? = null,

    @SerialName("sunset")
    val sunset: Int? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("id")
    val id: Int? = null,

    @SerialName("population")
    val population: Int? = null
)
