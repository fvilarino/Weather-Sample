package com.francescsoftware.weathersample.ui.feature.home

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.francescsoftware.weathersample.ui.feature.favorites.navigation.FavoritesRootDestination
import com.francescsoftware.weathersample.ui.feature.favorites.navigation.addFavoritesNavGraph
import com.francescsoftware.weathersample.ui.feature.search.navigation.SearchRootDestination
import com.francescsoftware.weathersample.ui.feature.search.navigation.addSearchNavGraph
import com.francescsoftware.weathersample.ui.shared.composable.common.AppBar
import com.francescsoftware.weathersample.ui.shared.deviceclass.DeviceClass
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import kotlinx.collections.immutable.persistentListOf

private val navGraphDestinations = persistentListOf(
    SearchRootDestination,
    FavoritesRootDestination,
)

@Composable
internal fun WeatherApp() {
    WeatherSampleTheme {
        val navController = rememberNavController()
        val windowSizeClass = calculateWindowSizeClass(LocalContext.current as Activity)
        val deviceClass = DeviceClass.fromWindowSizeClass(windowSizeClass = windowSizeClass)
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val currentDestination = navGraphDestinations.firstNotNullOfOrNull { navGraph ->
            navGraph.getDestination(currentRoute)
        } ?: navGraphDestinations.first().rootDestination
        Scaffold(
            contentWindowInsets = WindowInsets.statusBars,
            topBar = {
                AppBar(
                    title = currentDestination.title,
                    icon = currentDestination.icon,
                    iconContentDescription = currentDestination.iconContentDescription,
                    onIconClick = navController::popBackStack,
                    actions = { currentDestination.TopBarActions() },
                )
            },
            bottomBar = if (deviceClass.hasBottomNavBar) {
                {
                    AnimatedVisibility(
                        visible = currentDestination.showBottomBar,
                        enter = slideInVertically { it } + fadeIn(),
                        exit = slideOutVertically { it } + fadeOut(),
                    ) {
                        BottomNavBar(
                            items = navGraphDestinations,
                            currentDestination = navBackStackEntry?.destination,
                            onClick = { destination ->
                                navController.navigate(destination) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            } else {
                {}
            },
        ) { paddingValues ->
            Box {
                Row(
                    modifier = Modifier.padding(paddingValues),
                ) {
                    if (deviceClass == DeviceClass.Expanded) {
                        NavRail(
                            items = navGraphDestinations,
                            currentDestination = navBackStackEntry?.destination,
                            onClick = { destination ->
                                navController.navigate(destination) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        )
                    }
                    NavHost(
                        navController,
                        startDestination = navGraphDestinations.first().navGraphRoute,
                        modifier = Modifier.weight(1f),
                    ) {
                        addSearchNavGraph(
                            deviceClass = deviceClass,
                        ) { route ->
                            navController.navigate(route)
                        }
                        addFavoritesNavGraph(
                            deviceClass = deviceClass,
                        )
                    }
                }
                if (deviceClass.hasBottomNavBar && !currentDestination.showBottomBar) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .windowInsetsBottomHeight(WindowInsets.navigationBars)
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.background,
                                    )
                                )
                            )
                    )
                }
            }
        }
    }
}

private val DeviceClass.hasBottomNavBar: Boolean
    get() = this != DeviceClass.Expanded
