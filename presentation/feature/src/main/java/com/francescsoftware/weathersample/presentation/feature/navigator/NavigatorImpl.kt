package com.francescsoftware.weathersample.presentation.feature.navigator

import androidx.navigation.NavController
import javax.inject.Inject

class NavigatorImpl @Inject constructor() : Navigator {

    override fun onBackClick() {
        navController.popBackStack()
    }

    private lateinit var navController: NavController

    override fun setNavController(navController: NavController) {
        this.navController = navController
    }

    override fun cityToWeather() {
        navController.navigate(NavigationDestination.Weather.getRoute())
    }
}
