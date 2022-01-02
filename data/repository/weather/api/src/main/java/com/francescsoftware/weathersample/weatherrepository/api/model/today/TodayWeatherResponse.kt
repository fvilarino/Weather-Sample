package com.francescsoftware.weathersample.weatherrepository.api.model.today

import com.francescsoftware.weathersample.weatherrepository.api.model.Clouds
import com.francescsoftware.weathersample.weatherrepository.api.model.Coord
import com.francescsoftware.weathersample.weatherrepository.api.model.Main
import com.francescsoftware.weathersample.weatherrepository.api.model.Rain
import com.francescsoftware.weathersample.weatherrepository.api.model.Sys
import com.francescsoftware.weathersample.weatherrepository.api.model.WeatherItem
import com.francescsoftware.weathersample.weatherrepository.api.model.Wind
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodayWeatherResponse(

    @SerialName("rain")
    val rain: Rain? = null,

    @SerialName("visibility")
    val visibility: Int? = null,

    @SerialName("timezone")
    val timezone: Int? = null,

    @SerialName("main")
    val main: Main? = null,

    @SerialName("clouds")
    val clouds: Clouds? = null,

    @SerialName("sys")
    val sys: Sys? = null,

    @SerialName("dt")
    val dt: Int? = null,

    @SerialName("coord")
    val coord: Coord? = null,

    @SerialName("weather")
    val weather: List<WeatherItem>? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("cod")
    val cod: Int? = null,

    @SerialName("id")
    val id: Int? = null,

    @SerialName("base")
    val base: String? = null,

    @SerialName("wind")
    val wind: Wind? = null
)
