package com.francescsoftware.weathersample.ui.shared.weathericon

import androidx.annotation.DrawableRes

/** The set of available weather icons */
enum class WeatherIcon {
    ClearSky,
    FewClouds,
    ScatteredClouds,
    BrokenClouds,
    Overcast,
    ShowerRain,
    LightRain,
    ModerateRain,
    HeavyRain,
    Thunderstorm,
    Snow,
    Mist,
    Foggy,
    Windy,
}

/**
 * Maps an icon ID to a weather icon
 *
 * @param code the icon id
 * @return the [WeatherIcon] corresponding to the [code]
 */
@Suppress("MagicNumber")
fun weatherIconFromCode(code: Int): WeatherIcon = when (code) {
    1000 -> WeatherIcon.ClearSky
    1003 -> WeatherIcon.FewClouds
    1006 -> WeatherIcon.ScatteredClouds
    1009 -> WeatherIcon.Overcast
    1030 -> WeatherIcon.Mist
    1063,
    1150,
    1153,
    1168,
    1180,
    1183,
    1198,
    1204,
    1240,
    1249,
    1261,
    1273,
    -> WeatherIcon.LightRain

    1171,
    1192,
    1195,
    1246,
    -> WeatherIcon.HeavyRain

    1186,
    1189,
    1201,
    1207,
    1243,
    1252,
    1264,
    1276,
    -> WeatherIcon.ModerateRain

    1066,
    1069,
    1072,
    1114,
    1210,
    1213,
    1216,
    1219,
    1222,
    1225,
    1237,
    1255,
    1258,
    1279,
    1282,
    -> WeatherIcon.Snow

    1087 -> WeatherIcon.Thunderstorm
    1117 -> WeatherIcon.Windy
    1147 -> WeatherIcon.Foggy
    else -> WeatherIcon.ClearSky
}

/** The [DrawableRes] for a [WeatherIcon] */
val WeatherIcon.drawableId: Int
    @DrawableRes
    get() = when (this) {
        WeatherIcon.ClearSky -> com.francescsoftware.weathersample.ui.shared.assets.R.drawable.ic_sunny
        WeatherIcon.FewClouds,
        WeatherIcon.ScatteredClouds,
        WeatherIcon.BrokenClouds,
        -> com.francescsoftware.weathersample.ui.shared.assets.R.drawable.ic_partly_cloudy

        WeatherIcon.Overcast -> com.francescsoftware.weathersample.ui.shared.assets.R.drawable.ic_overcast
        WeatherIcon.ShowerRain,
        WeatherIcon.LightRain,
        -> com.francescsoftware.weathersample.ui.shared.assets.R.drawable.ic_light_rain

        WeatherIcon.ModerateRain -> com.francescsoftware.weathersample.ui.shared.assets.R.drawable.ic_moderate_rain
        WeatherIcon.HeavyRain -> com.francescsoftware.weathersample.ui.shared.assets.R.drawable.ic_heavy_rain
        WeatherIcon.Thunderstorm -> com.francescsoftware.weathersample.ui.shared.assets.R.drawable.ic_thunder
        WeatherIcon.Snow -> com.francescsoftware.weathersample.ui.shared.assets.R.drawable.ic_snow
        WeatherIcon.Mist,
        WeatherIcon.Foggy,
        -> com.francescsoftware.weathersample.ui.shared.assets.R.drawable.ic_foggy

        WeatherIcon.Windy -> com.francescsoftware.weathersample.ui.shared.assets.R.drawable.ic_windy
    }
