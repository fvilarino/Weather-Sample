package com.francescsoftware.weathersample.ui.feature.favorites.navigation

import com.francescsoftware.weathersample.ui.shared.route.BottomNavigationDestination
import com.francescsoftware.weathersample.ui.shared.route.NavigationDestination
import com.francescsoftware.weathersample.ui.shared.route.RootNavigationDestination

/** The root for the favorites nav graph */
object FavoritesRootDestination : RootNavigationDestination {
    /** @{inheritDoc} */
    override val navGraphRoute: String = "favorites_nav_graph"

    /** @{inheritDoc} */
    override val rootDestination: BottomNavigationDestination = FavoritesDestination

    /** @{inheritDoc} */
    override val destinations: List<NavigationDestination> = listOf(
        FavoritesDestination
    )

    /** @{inheritDoc} */
    override fun getDestination(route: String?): NavigationDestination? = when (route) {
        FavoritesDestination.route -> FavoritesDestination
        else -> null
    }
}
