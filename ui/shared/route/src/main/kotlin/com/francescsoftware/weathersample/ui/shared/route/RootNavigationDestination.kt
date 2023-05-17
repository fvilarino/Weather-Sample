package com.francescsoftware.weathersample.ui.shared.route

/** A [NavigationDestination] that is the root of nested navigation graph */
interface RootNavigationDestination {
    /** The route to navigate to this nested navigation graph */
    val navGraphRoute: String

    /** The root of this nested navigation graph */
    val rootDestination: BottomNavigationDestination

    /** The different destinations that are part of this nested navigation graph */
    val destinations: List<NavigationDestination>

    /**
     * Gets the [NavigationDestination] matching the [route] if found, null otherwise
     *
     * @param route the route to find a [NavigationDestination] for
     * @return the [NavigationDestination] matching the [route], null if none found
     */
    fun getDestination(route: String?): NavigationDestination?
}
