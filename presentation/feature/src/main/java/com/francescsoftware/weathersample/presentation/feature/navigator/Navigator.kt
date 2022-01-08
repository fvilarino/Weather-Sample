package com.francescsoftware.weathersample.presentation.feature.navigator

import androidx.navigation.NavController
import com.francescsoftware.weathersample.storage.city.api.SelectedCity

interface Navigator {
    fun onBackClick()
    fun setNavController(navController: NavController)
    fun cityToWeather(selectedCity: SelectedCity)
}
