package com.francescsoftware.weathersample.feature.city

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.francescsoftware.weathersample.feature.city.viewmodel.CityViewModel
import com.francescsoftware.weathersample.feature.navigation.api.NavigationDestination
import com.francescsoftware.weathersample.feature.navigation.api.SelectedCity

fun NavGraphBuilder.addSearchDestination(
    onCityClick: (SelectedCity) -> Unit,
) {
    composable(
        route = NavigationDestination.CitySearch.getRoute()
    ) {
        val cityViewModel: CityViewModel = hiltViewModel()
        CityScreen(
            viewModel = cityViewModel,
            onCityClick = onCityClick,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
