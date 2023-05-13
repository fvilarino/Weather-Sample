package com.francescsoftware.weathersample.feature.city

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.francescsoftware.weathersample.deviceclass.DeviceClass
import com.francescsoftware.weathersample.feature.city.ui.CityScreen
import com.francescsoftware.weathersample.feature.city.viewmodel.CityViewModel
import com.francescsoftware.weathersample.presentation.route.CitySearchDestination
import com.francescsoftware.weathersample.presentation.route.SelectedCity

/**
 * Add City Search destination to Nav Graph
 *
 * @param deviceClass - The [DeviceClass] the composable is running on
 * @param onCityClick - Lambda to trigger on city click
 * @receiver The [NavGraphBuilder] this destination should be added to.
 */
fun NavGraphBuilder.addSearchDestination(
    deviceClass: DeviceClass,
    onCityClick: (SelectedCity) -> Unit,
) {
    composable(
        route = CitySearchDestination.route,
    ) {
        val cityViewModel: CityViewModel = hiltViewModel()
        CityScreen(
            viewModel = cityViewModel,
            deviceClass = deviceClass,
            onCityClick = onCityClick,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
