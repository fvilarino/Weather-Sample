package com.francescsoftware.weathersample.presentation.feature.navigator

import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.presentation.feature.weather.SelectedCity
import com.francescsoftware.weathersample.presentation.shared.lookup.StringLookup
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class NavigatorImpl @Inject constructor(
    private val stringLookup: StringLookup,
) : Navigator {

    override fun onBackClick() {
        navController.popBackStack()
        currentDestination.value = CurrentDestination(
            isRoot = true,
            title = stringLookup.getString(NavigationDestination.CitySearch.titleId),
            icon = NavigationDestination.CitySearch.iconId,
        )
    }

    override val currentDestination = MutableStateFlow<CurrentDestination>(
        CurrentDestination(
            isRoot = true,
            icon = 0,
            title = stringLookup.getString(R.string.app_name),
        )
    )

    private lateinit var navController: NavController

    override fun setNavController(navController: NavController) {
        this.navController = navController
    }

    override fun cityToWeather(city: SelectedCity) {
        navController.navigate(NavigationDestination.Weather.getDestination(city))
        currentDestination.value = CurrentDestination(
            isRoot = false,
            title = stringLookup.getString(NavigationDestination.Weather.titleId),
            icon = NavigationDestination.Weather.iconId,
        )
    }
}
