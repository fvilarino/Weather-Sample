package com.francescsoftware.weathersample.ui.feature.weather

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.francescsoftware.weathersample.ui.feature.weather.ui.WeatherScreen
import com.francescsoftware.weathersample.ui.feature.weather.viewmodel.WeatherViewModel
import com.francescsoftware.weathersample.ui.shared.deviceclass.DeviceClass
import com.francescsoftware.weathersample.ui.shared.route.WeatherDestination

/** Adds the Weather details destination to the Navigation Graph */
fun NavGraphBuilder.addWeatherDetailsDestination(
    deviceClass: DeviceClass,
) {
    composable(
        route = WeatherDestination.route,
    ) {
        val weatherViewModel: WeatherViewModel = hiltViewModel()
        WeatherScreen(
            viewModel = weatherViewModel,
            deviceClass = deviceClass,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
