package com.francescsoftware.weathersample.weatherrepository.api.model.forecast

import com.francescsoftware.weathersample.weatherrepository.api.model.Clouds
import com.francescsoftware.weathersample.weatherrepository.api.model.Main
import com.francescsoftware.weathersample.weatherrepository.api.model.Rain
import com.francescsoftware.weathersample.weatherrepository.api.model.Snow
import com.francescsoftware.weathersample.weatherrepository.api.model.Sys
import com.francescsoftware.weathersample.weatherrepository.api.model.WeatherItem
import com.francescsoftware.weathersample.weatherrepository.api.model.Wind
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastItem(

    @SerialName("dt")
    val epoch: Int? = null,

    @SerialName("pop")
    val pop: Double? = null,

    @SerialName("visibility")
    val visibility: Int? = null,

    @SerialName("dt_txt")
    val dtTxt: String? = null,

    @SerialName("weather")
    val weather: List<WeatherItem>? = null,

    @SerialName("main")
    val main: Main? = null,

    @SerialName("clouds")
    val clouds: Clouds? = null,

    @SerialName("sys")
    val sys: Sys? = null,

    @SerialName("wind")
    val wind: Wind? = null,

    @SerialName("rain")
    val rain: Rain? = null,

    @SerialName("snow")
    val snow: Snow? = null
)
