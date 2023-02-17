package com.francescsoftware.weathersample.presentation.route

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

/** Navigation destination */
sealed interface NavigationDestination {
    /** Title to display on the action bar */
    @get: StringRes
    val titleId: Int

    /** Icon ID to draw on the top left of the action bar */
    @get: DrawableRes
    val iconId: Int

    /** Content desctiption for hte [iconId] */
    @get: StringRes
    val iconContentDescriptionId: Int

    /**
     * Checks whether the [route] represents this destination
     *
     * @param route - the route to match
     * @return true if the route matches this destination, false otherwise
     */
    fun isRoute(route: String?): Boolean

    /** Composable rendering the top bar actions */
    @Composable
    fun TopBarActions() {
    }

    companion object {
        /**
         * Maps a route toa [NavigationDestination]
         *
         * @param route - the route to map
         * @return a [NavigationDestination] matching the [route]
         */
        fun fromRoute(route: String?): NavigationDestination = when {
            route == null ||
                CitySearchDestination.isRoute(route) -> CitySearchDestination

            WeatherDestination.isRoute(route) -> WeatherDestination
            else -> error("Unknown route [$route]")
        }
    }
}
