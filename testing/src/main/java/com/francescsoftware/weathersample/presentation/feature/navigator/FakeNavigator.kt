package com.francescsoftware.weathersample.presentation.feature.navigator

import androidx.navigation.NavController

class FakeNavigator : Navigator {
    override fun onBackClick() = Unit

    override fun setNavController(navController: NavController) = Unit

    override fun cityToWeather(selectedCity: NavigationDestination.Weather.SelectedCity) = Unit
}
