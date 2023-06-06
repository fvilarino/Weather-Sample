package com.francescsoftware.weathersample.ui.feature.home

import android.app.Activity
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.francescsoftware.weathersample.ui.shared.deviceclass.DeviceClass
import com.francescsoftware.weathersample.ui.shared.route.NavigationDestination

internal class AppState(
    val navHostController: NavHostController,
    val deviceClass: DeviceClass,
) {
    val navBackEntryDestination: NavDestination?
        @Composable
        get() = navBackStackEntry?.destination

    val currentDestination: NavigationDestination
        @Composable
        get() = navGraphDestinations.firstNotNullOfOrNull { navGraph ->
            navGraph.getDestination(currentRoute)
        } ?: navGraphDestinations.first().rootDestination

    val hasBottomNavBar: Boolean
        get() = deviceClass.hasBottomNavBar

    val hasNavRail: Boolean
        get() = !hasBottomNavBar

    val showBottomNavBar: Boolean
        @Composable
        get() = hasBottomNavBar && currentDestination.showBottomBar

    val showBottomOverlay: Boolean
        @Composable
        get() = hasBottomNavBar && !currentDestination.showBottomBar

    private val navBackStackEntry: NavBackStackEntry?
        @Composable
        get() = navHostController.currentBackStackEntryAsState().value

    private val currentRoute: String?
        @Composable
        get() = navBackStackEntry?.destination?.route

    fun navigate(
        destination: String,
    ) {
        navHostController.navigate(destination)
    }

    fun navigateToTopDestination(
        destination: String,
    ) {
        with(navHostController) {
            navigate(destination) {
                popUpTo(graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    fun popBackstack() {
        navHostController.popBackStack()
    }

    private val DeviceClass.hasBottomNavBar: Boolean
        get() = this != DeviceClass.Expanded
}

@Composable
internal fun rememberAppState(
    navHostController: NavHostController = rememberNavController(),
): AppState {
    val windowSizeClass = calculateWindowSizeClass(LocalContext.current as Activity)
    val deviceClass = DeviceClass.fromWindowSizeClass(windowSizeClass = windowSizeClass)
    return remember(key1 = navHostController, key2 = deviceClass) {
        AppState(
            navHostController = navHostController,
            deviceClass = deviceClass,
        )
    }
}
