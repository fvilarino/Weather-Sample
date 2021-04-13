package com.francescsoftware.weathersample.presentation.feature.navigator

import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.presentation.feature.search.CityFragmentDirections
import com.francescsoftware.weathersample.presentation.feature.weather.SelectedCity
import com.francescsoftware.weathersample.presentation.shared.extension.locateActivity
import javax.inject.Inject

class NavigatorImpl @Inject constructor() : Navigator {

    private var owner: LifecycleOwner? = null
    private val navController: NavController?
        get() {
            val activity = owner?.locateActivity
            return if (activity != null) {
                val navHostFragment = activity
                    .supportFragmentManager
                    .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                navHostFragment.navController
            } else {
                null
            }
        }

    override fun onStart(owner: LifecycleOwner) {
        this.owner = owner
    }

    override fun onStop(owner: LifecycleOwner) {
        this.owner = null
    }

    override fun cityToWeather(city: SelectedCity) {
        CityFragmentDirections.actionCityFragmentToWeatherFragment(
            city = city
        ).navigate()
    }

    override fun goBack() {
        navController?.popBackStack()
    }

    private fun NavDirections.navigate() {
        navController?.navigate(this)
    }
}
