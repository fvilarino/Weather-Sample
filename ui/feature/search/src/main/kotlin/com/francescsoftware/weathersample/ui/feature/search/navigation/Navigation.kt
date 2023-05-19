package com.francescsoftware.weathersample.ui.feature.search.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.francescsoftware.weathersample.ui.feature.search.city.ui.CityScreen
import com.francescsoftware.weathersample.ui.feature.search.city.viewmodel.CityViewModel
import com.francescsoftware.weathersample.ui.feature.search.weather.ui.WeatherScreen
import com.francescsoftware.weathersample.ui.feature.search.weather.viewmodel.WeatherViewModel
import com.francescsoftware.weathersample.ui.shared.deviceclass.DeviceClass

/**
 * Add City Search destination to Nav Graph
 *
 * @param deviceClass the [DeviceClass] the composable is running on
 * @param onNavigate lambda to trigger on city click
 * @receiver the [NavGraphBuilder] this destination should be added to.
 */
fun NavGraphBuilder.addSearchNavGraph(
    deviceClass: DeviceClass,
    onNavigate: (String) -> Unit,
) {
    navigation(
        startDestination = SearchRootDestination.rootDestination.route,
        route = SearchRootDestination.navGraphRoute,
    ) {
        composable(
            route = CitySearchDestination.route,
        ) {
            val cityViewModel: CityViewModel = hiltViewModel()
            CityScreen(
                viewModel = cityViewModel,
                deviceClass = deviceClass,
                onCityClick = { selectedCity -> onNavigate(WeatherDestination.getRoute(selectedCity)) },
                modifier = Modifier.fillMaxSize(),
            )
        }
        composable(
            route = WeatherDestination.route,
            deepLinks = WeatherDestination.deeplinks,
        ) {
            val weatherViewModel: WeatherViewModel = hiltViewModel()
            WeatherScreen(
                viewModel = weatherViewModel,
                deviceClass = deviceClass,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
