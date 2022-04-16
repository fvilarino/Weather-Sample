package com.francescsoftware.weathersample.feature.city

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.francescsoftware.weathersample.feature.city.viewmodel.CityViewModel
import com.francescsoftware.weathersample.feature.navigation.api.NavigationDestination

fun NavGraphBuilder.addSearchDestination() {
    composable(
        route = NavigationDestination.CitySearch.getRoute()
    ) {
        val cityViewModel: CityViewModel = hiltViewModel()
        CityScreen(
            viewModel = cityViewModel,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
