package com.francescsoftware.weathersample.feature.city

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.francescsoftware.weathersample.feature.navigation.api.NavigationDestination

fun NavGraphBuilder.addSearchDestination() {
    composable(
        route = NavigationDestination.CitySearch.getRoute()
    ) {
        val cityViewModel: CityViewModel = hiltViewModel()
        CityScreen(cityViewModel)
    }
}
