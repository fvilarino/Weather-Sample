package com.francescsoftware.weathersample.ui.shared.route

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Payload for bottom navigation destinations
 *
 * @property labelId the label to display on the bottom nav bar
 * @property icon the icon to display on the bottom nav bar
 * @property contentDescriptionId the content description associated with this nav bar item
 */
data class BottomNavContent(
    @get: StringRes
    val labelId: Int,

    val icon: ImageVector,

    @get: StringRes
    val contentDescriptionId: Int
)

/** Navigation destination */
sealed interface NavigationDestination {

    /** This [NavigationDestination] route */
    val route: String

    /** Title to display on the action bar */
    @get: StringRes
    val titleId: Int

    /** Icon ID to draw on the top left of the action bar */
    @get: DrawableRes
    val iconId: Int

    /** Content description for hte [iconId] */
    @get: StringRes
    val iconContentDescriptionId: Int

    /** The content for the bottom navigation bar */
    val bottomNavContent: BottomNavContent?

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
            FavoritesDestination.isRoute(route) -> FavoritesDestination
            else -> error("Unknown route [$route]")
        }
    }
}
