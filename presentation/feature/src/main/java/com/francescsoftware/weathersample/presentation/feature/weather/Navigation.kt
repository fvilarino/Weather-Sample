package com.francescsoftware.weathersample.presentation.feature.weather

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.francescsoftware.weathersample.presentation.feature.navigator.NavigationDestination

fun NavGraphBuilder.addWeatherDetailsDestination() {
    composable(
        route = NavigationDestination.Weather.weatherRoute,
    ) {
        val weatherViewModel: WeatherViewModel = hiltViewModel()
        WeatherScreen(weatherViewModel)
    }
}
