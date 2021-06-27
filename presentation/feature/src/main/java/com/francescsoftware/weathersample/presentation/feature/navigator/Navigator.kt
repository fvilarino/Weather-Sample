package com.francescsoftware.weathersample.presentation.feature.navigator

import androidx.navigation.NavController

interface Navigator {
    fun onBackClick()
    fun setNavController(navController: NavController)
    fun cityToWeather()
}
