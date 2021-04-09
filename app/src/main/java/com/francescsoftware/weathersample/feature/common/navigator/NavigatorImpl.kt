package com.francescsoftware.weathersample.feature.common.navigator

import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.francescsoftware.weathersample.R
import com.francescsoftware.weathersample.feature.common.extension.localteActivity
import com.francescsoftware.weathersample.feature.search.CityFragmentDirections
import com.francescsoftware.weathersample.feature.weather.SelectedCity
import javax.inject.Inject

class NavigatorImpl @Inject constructor(): Navigator {

    private var owner: LifecycleOwner? = null
    private val navController: NavController?
        get() = owner?.localteActivity?.findNavController(
            R.id.nav_host_fragment
        )

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
