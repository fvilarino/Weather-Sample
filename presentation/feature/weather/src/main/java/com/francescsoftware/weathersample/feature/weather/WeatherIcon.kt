package com.francescsoftware.weathersample.feature.weather

import androidx.annotation.DrawableRes

internal enum class WeatherIcon(
    val dayId: String,
    val nightId: String,
    @DrawableRes val iconId: Int
) {
    ClearSky("01d", "01n", R.drawable.ic_sunny),
    FewClouds("02d", "02n", R.drawable.ic_partly_cloudy),
    ScatteredClouds("03d", "03n", R.drawable.ic_overcast),
    BrokenClouds("04d", "04n", R.drawable.ic_partly_cloudy),
    ShowerRain("09d", "09n", R.drawable.ic_light_rain),
    Rain("10d", "10n", R.drawable.ic_moderate_rain),
    Thunderstorm("11d", "11n", R.drawable.ic_thunder),
    Snow("13d", "13n", R.drawable.ic_snow),
    Mist("50d", "50n", R.drawable.ic_foggy);

    companion object {
        fun fromIconId(id: String) = values().firstOrNull { weatherIcon ->
            weatherIcon.dayId == id || weatherIcon.nightId == id
        } ?: ClearSky
    }
}
