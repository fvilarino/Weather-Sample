package com.francescsoftware.weathersample.feature.weather

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.francescsoftware.weathersample.feature.weather.ui.WeatherScreen
import com.francescsoftware.weathersample.feature.weather.viewmodel.WeatherViewModel
import com.francescsoftware.weathersample.presentation.route.NavigationDestination

fun NavGraphBuilder.addWeatherDetailsDestination() {
    composable(
        route = NavigationDestination.Weather.weatherRoute,
    ) {
        val weatherViewModel: WeatherViewModel = hiltViewModel()
        WeatherScreen(
            viewModel = weatherViewModel,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
