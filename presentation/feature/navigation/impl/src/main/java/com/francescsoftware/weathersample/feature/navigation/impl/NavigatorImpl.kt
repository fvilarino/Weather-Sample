package com.francescsoftware.weathersample.feature.navigation.impl

import androidx.navigation.NavController
import com.francescsoftware.weathersample.feature.navigation.api.NavigationDestination
import com.francescsoftware.weathersample.feature.navigation.api.Navigator
import com.francescsoftware.weathersample.feature.navigation.api.SelectedCity
import javax.inject.Inject

class NavigatorImpl @Inject constructor() : Navigator {

    override fun onBackClick() {
        navController.popBackStack()
    }

    private lateinit var navController: NavController

    override fun setNavController(navController: NavController) {
        this.navController = navController
    }

    override fun cityToWeather(selectedCity: SelectedCity) {
        navController.navigate(NavigationDestination.Weather.getRoute(selectedCity))
    }
}
