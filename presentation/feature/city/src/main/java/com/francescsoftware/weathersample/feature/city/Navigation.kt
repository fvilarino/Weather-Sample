package com.francescsoftware.weathersample.feature.city

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.francescsoftware.weathersample.deviceclass.DeviceClass
import com.francescsoftware.weathersample.feature.city.ui.CityScreen
import com.francescsoftware.weathersample.feature.city.viewmodel.CityViewModel
import com.francescsoftware.weathersample.presentation.route.NavigationDestination
import com.francescsoftware.weathersample.presentation.route.SelectedCity

fun NavGraphBuilder.addSearchDestination(
    deviceClass: DeviceClass,
    onCityClick: (SelectedCity) -> Unit,
) {
    composable(
        route = NavigationDestination.CitySearch.getRoute(),
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
