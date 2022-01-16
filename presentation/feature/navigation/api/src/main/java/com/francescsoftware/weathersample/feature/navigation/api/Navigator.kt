package com.francescsoftware.weathersample.feature.navigation.api

import androidx.navigation.NavController

interface Navigator {
    fun onBackClick()
    fun setNavController(navController: NavController)
    fun cityToWeather(selectedCity: SelectedCity)
}
