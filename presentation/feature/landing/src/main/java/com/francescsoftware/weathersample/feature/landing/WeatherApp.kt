package com.francescsoftware.weathersample.feature.landing

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.francescsoftware.weathersample.deviceclass.DeviceClass
import com.francescsoftware.weathersample.feature.city.addSearchDestination
import com.francescsoftware.weathersample.feature.weather.addWeatherDetailsDestination
import com.francescsoftware.weathersample.presentation.route.NavigationDestination
import com.francescsoftware.weathersample.shared.composable.AppBar
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

@Composable
internal fun WeatherApp() {
    WeatherSampleTheme {
        val navController = rememberNavController()
        val windowSizeClass = calculateWindowSizeClass(LocalContext.current as Activity)
        val deviceClass = DeviceClass.fromWindowSizeClass(windowSizeClass = windowSizeClass)
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val currentDestination = when {
            NavigationDestination.CitySearch.isRoute(currentRoute) -> NavigationDestination.CitySearch
            NavigationDestination.Weather.isRoute(currentRoute) -> NavigationDestination.Weather
            else -> NavigationDestination.CitySearch
        }

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
                    modifier = Modifier.systemBarsPadding(),
                )
            },
        ) { paddingValues ->
            NavHost(
                navController,
                startDestination = NavigationDestination.CitySearch.cityRoute,
                modifier = Modifier.padding(paddingValues),
            ) {
                addSearchDestination(
                    deviceClass = deviceClass,
                    onCityClick = { selectedCity ->
                        navController.navigate(NavigationDestination.Weather.getRoute(selectedCity))
                    }
                )
                addWeatherDetailsDestination()
            }
        }
    }
}