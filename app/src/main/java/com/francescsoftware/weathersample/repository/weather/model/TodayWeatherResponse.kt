package com.francescsoftware.weathersample.repository.weather.model

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

@Serializable
data class Main(

    @SerialName("temp")
    val temp: Double? = null,

    @SerialName("temp_min")
    val tempMin: Double? = null,

    @SerialName("humidity")
    val humidity: Int? = null,

    @SerialName("pressure")
    val pressure: Int? = null,

    @SerialName("feels_like")
    val feelsLike: Double? = null,

    @SerialName("temp_max")
    val tempMax: Double? = null
)

@Serializable
data class Rain(

    @SerialName("1h")
    val oneHour: Double? = null
)

@Serializable
data class Coord(

    @SerialName("lon")
    val lon: Double? = null,

    @SerialName("lat")
    val lat: Double? = null
)

@Serializable
data class WeatherItem(

    @SerialName("icon")
    val icon: String? = null,

    @SerialName("description")
    val description: String? = null,

    @SerialName("main")
    val main: String? = null,

    @SerialName("id")
    val id: Int? = null
)

@Serializable
data class Clouds(

    @SerialName("all")
    val all: Int? = null
)

@Serializable
data class Sys(

    @SerialName("country")
    val country: String? = null,

    @SerialName("sunrise")
    val sunrise: Int? = null,

    @SerialName("sunset")
    val sunset: Int? = null,

    @SerialName("id")
    val id: Int? = null,

    @SerialName("type")
    val type: Int? = null
)

@Serializable
data class Wind(

    @SerialName("deg")
    val deg: Int? = null,

    @SerialName("speed")
    val speed: Double? = null,

    @SerialName("gust")
    val gust: Double? = null
)
