package com.francescsoftware.weathersample.feature.weather

import androidx.annotation.DrawableRes

enum class WeatherIcon(
    val dayId: String,
    val nightId: String,
    @DrawableRes val iconId: Int
) {
    CLEAR_SKY("01d", "01n", R.drawable.ic_sunny),
    FEW_CLOUDS("02d", "02n", R.drawable.ic_partly_cloudy),
    SCATTERED_CLOUDS("03d", "03n", R.drawable.ic_overcast),
    BROKEN_CLOUDS("04d", "04n", R.drawable.ic_partly_cloudy),
    SHOWER_RAIN("09d", "09n", R.drawable.ic_light_rain),
    RAIN("10d", "10n", R.drawable.ic_moderate_rain),
    THUNDERSTORM9("11d", "11n", R.drawable.ic_thunder),
    SNOW("13d", "13n", R.drawable.ic_snow),
    MIST("50d", "50n", R.drawable.ic_foggy);

    companion object {
        fun fromIconId(id: String) = values().firstOrNull { weatherIcon ->
            weatherIcon.dayId == id || weatherIcon.nightId == id
        } ?: CLEAR_SKY
    }
}
