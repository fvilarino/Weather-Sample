package com.francescsoftware.weathersample.presentation.route

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

sealed interface NavigationDestination {
    @get: StringRes
    val titleId: Int

    @get: DrawableRes
    val iconId: Int

    @get: StringRes
    val iconContentDescriptionId: Int

    fun isRoute(route: String?): Boolean

    @Composable
    fun TopBarActions() {
    }

    companion object {
        fun fromRoute(route: String?): NavigationDestination = when {
            route == null ||
                CitySearchDestination.isRoute(route) -> CitySearchDestination

            WeatherDestination.isRoute(route) -> WeatherDestination
            else -> throw IllegalStateException("Unknown route [$route]")
        }
    }
}
