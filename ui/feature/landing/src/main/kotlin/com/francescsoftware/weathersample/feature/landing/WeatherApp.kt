package com.francescsoftware.weathersample.feature.landing

import android.app.Activity
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.francescsoftware.weathersample.deviceclass.DeviceClass
import com.francescsoftware.weathersample.feature.city.addSearchDestination
import com.francescsoftware.weathersample.feature.weather.addWeatherDetailsDestination
import com.francescsoftware.weathersample.presentation.route.CitySearchDestination
import com.francescsoftware.weathersample.presentation.route.NavigationDestination
import com.francescsoftware.weathersample.presentation.route.WeatherDestination
import com.francescsoftware.weathersample.shared.composable.common.AppBar
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

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
        ) { paddingValues ->
            val layoutDirection = LocalLayoutDirection.current
            NavHost(
                navController,
                startDestination = CitySearchDestination.cityRoute,
                modifier = Modifier.padding(
                    top = paddingValues.calculateTopPadding(),
                    start = paddingValues.calculateStartPadding(layoutDirection),
                    end = paddingValues.calculateEndPadding(layoutDirection),
                )
            ) {
                addSearchDestination(
                    deviceClass = deviceClass,
                ) { selectedCity ->
                    navController.navigate(WeatherDestination.getRoute(selectedCity))
                }
                addWeatherDetailsDestination(
                    deviceClass = deviceClass,
                )
            }
        }
    }
}
