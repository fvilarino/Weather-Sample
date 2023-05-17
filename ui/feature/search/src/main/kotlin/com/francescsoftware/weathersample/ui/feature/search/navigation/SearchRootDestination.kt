package com.francescsoftware.weathersample.ui.feature.search.navigation

import com.francescsoftware.weathersample.ui.shared.route.BottomNavigationDestination
import com.francescsoftware.weathersample.ui.shared.route.NavigationDestination
import com.francescsoftware.weathersample.ui.shared.route.RootNavigationDestination

/** The root for the search nested navigation graph */
object SearchRootDestination : RootNavigationDestination {
    /** @{inheritDoc} */
    override val navGraphRoute: String = "search_nav_graph"

    /** @{inheritDoc} */
    override val rootDestination: BottomNavigationDestination = CitySearchDestination

    /** @{inheritDoc} */
    override val destinations: List<NavigationDestination> = listOf(
        CitySearchDestination,
        WeatherDestination,
    )

    /** @{inheritDoc} */
    override fun getDestination(route: String?): NavigationDestination? = when (route) {
        CitySearchDestination.route -> CitySearchDestination
        WeatherDestination.route -> WeatherDestination
        else -> null
    }
}
