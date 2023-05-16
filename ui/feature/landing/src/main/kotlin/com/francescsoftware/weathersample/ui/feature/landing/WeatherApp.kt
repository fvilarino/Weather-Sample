package com.francescsoftware.weathersample.ui.feature.landing

import android.app.Activity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.francescsoftware.weathersample.ui.feature.city.addSearchDestination
import com.francescsoftware.weathersample.ui.feature.favorites.addFavoritesDestination
import com.francescsoftware.weathersample.ui.feature.weather.addWeatherDetailsDestination
import com.francescsoftware.weathersample.ui.shared.composable.common.AppBar
import com.francescsoftware.weathersample.ui.shared.deviceclass.DeviceClass
import com.francescsoftware.weathersample.ui.shared.route.CitySearchDestination
import com.francescsoftware.weathersample.ui.shared.route.FavoritesDestination
import com.francescsoftware.weathersample.ui.shared.route.NavigationDestination
import com.francescsoftware.weathersample.ui.shared.route.WeatherDestination
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import kotlinx.collections.immutable.persistentListOf

private val bottomBarItems = persistentListOf(
    CitySearchDestination,
    FavoritesDestination,
)

@Composable
internal fun WeatherApp() {
    WeatherSampleTheme {
        val navController = rememberNavController()
        val windowSizeClass = calculateWindowSizeClass(LocalContext.current as Activity)
        val deviceClass = DeviceClass.fromWindowSizeClass(windowSizeClass = windowSizeClass)
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val currentDestination = NavigationDestination.fromRoute(currentRoute)
        Scaffold(
            topBar = {
                AppBar(
                    title = stringResource(id = currentDestination.titleId),
                    iconId = currentDestination.iconId,
                    iconContentDescription = if (currentDestination.iconContentDescriptionId != 0) {
                        stringResource(id = currentDestination.iconContentDescriptionId)
                    } else {
                        null
                    },
                    onIconClick = navController::popBackStack,
                    actions = { currentDestination.TopBarActions() },
                )
            },
            bottomBar = if (deviceClass == DeviceClass.Expanded) {
                {}
            } else {
                {
                    BottomNavBar(
                        items = bottomBarItems,
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
            },
        ) { paddingValues ->
            Row(
                modifier = Modifier.padding(paddingValues),
            ) {
                if (deviceClass == DeviceClass.Expanded) {
                    NavRail(
                        items = bottomBarItems,
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
                    startDestination = CitySearchDestination.route,
                    modifier = Modifier.weight(1f),
                ) {
                    addSearchDestination(
                        deviceClass = deviceClass,
                    ) { selectedCity ->
                        navController.navigate(WeatherDestination.getRoute(selectedCity))
                    }
                    addWeatherDetailsDestination(
                        deviceClass = deviceClass,
                    )
                    addFavoritesDestination(
                        deviceClass = deviceClass,
                    )
                }
            }
        }
    }
}
