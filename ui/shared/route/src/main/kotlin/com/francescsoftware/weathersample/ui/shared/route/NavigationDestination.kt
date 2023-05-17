package com.francescsoftware.weathersample.ui.shared.route

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

/** Navigation destination */
interface NavigationDestination {

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
