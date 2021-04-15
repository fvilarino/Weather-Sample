package com.francescsoftware.weathersample.presentation.feature.navigator

import androidx.navigation.NavController
import com.francescsoftware.weathersample.presentation.feature.weather.SelectedCity
import kotlinx.coroutines.flow.StateFlow

interface Navigator {
    val currentDestination: StateFlow<CurrentDestination>

    fun onBackClick()
    fun setNavController(navController: NavController)
    fun cityToWeather(city: SelectedCity)
}
