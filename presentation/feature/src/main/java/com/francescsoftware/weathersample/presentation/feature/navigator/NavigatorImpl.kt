package com.francescsoftware.weathersample.presentation.feature.navigator

import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.francescsoftware.weathersample.presentation.feature.weather.SelectedCity
import javax.inject.Inject

class NavigatorImpl @Inject constructor() : Navigator {

    override fun onBackClick() {
        navController.popBackStack()
    }

    private lateinit var navController: NavController

    override fun setNavController(navController: NavController) {
        this.navController = navController
    }

    override fun cityToWeather(city: SelectedCity) {
        navController.navigate(NavigationDestination.Weather.getDestination(city))
    }
}
