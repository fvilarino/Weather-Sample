package com.francescsoftware.weathersample.presentation.feature.search

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.francescsoftware.weathersample.presentation.feature.navigator.NavigationDestination

fun NavGraphBuilder.addSearchDestination() {
    composable(
        route = NavigationDestination.CitySearch.getRoute()
    ) {
        val cityViewModel: CityViewModel = hiltViewModel()
        CityScreen(cityViewModel)
    }
}
