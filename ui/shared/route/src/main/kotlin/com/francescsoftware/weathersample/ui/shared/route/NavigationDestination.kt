package com.francescsoftware.weathersample.ui.shared.route

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

/** Navigation destination */
interface NavigationDestination {

    /** This [NavigationDestination] route */
    val route: String

    /** Title to display on the action bar */
    @get: Composable
    val title: String

    /** Icon ID to draw on the top left of the action bar */
    @get: Composable
    val icon: ImageVector?

    /** Content description for hte [icon] */
    @get: Composable
    val iconContentDescription: String?

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
}
