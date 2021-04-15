package com.francescsoftware.weathersample.presentation.feature.navigator

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.francescsoftware.weathersample.presentation.feature.R

enum class NavigationDestination(
    val route: String,
    @StringRes val titleId: Int,
    @DrawableRes val iconId: Int
) {
    CitySearch(
        route = "city_search",
        titleId = R.string.app_name,
        iconId = 0,
    ),
    Weather(
        route = "weather",
        titleId = R.string.weather_label,
        iconId = R.drawable.ic_arrow_back,
    )
}
