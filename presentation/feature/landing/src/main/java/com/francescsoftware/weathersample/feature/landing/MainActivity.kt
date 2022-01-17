package com.francescsoftware.weathersample.feature.landing

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.francescsoftware.weathersample.feature.city.addSearchDestination
import com.francescsoftware.weathersample.feature.navigation.api.NavigationDestination
import com.francescsoftware.weathersample.feature.navigation.api.Navigator
import com.francescsoftware.weathersample.feature.weather.addWeatherDetailsDestination
import com.francescsoftware.weathersample.shared.composable.AppBar
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherSampleTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val currentDestination = when {
                    NavigationDestination.CitySearch.isRoute(currentRoute) -> NavigationDestination.CitySearch
                    NavigationDestination.Weather.isRoute(currentRoute) -> NavigationDestination.Weather
                    else -> NavigationDestination.CitySearch
                }
                Scaffold(
                    topBar = {
                        AppBar(
                            titleId = currentDestination.titleId,
                            iconId = currentDestination.iconId,
                            onIconClick = { navigator.onBackClick() },
                        )
                    },
                ) {
                    NavHost(
                        navController,
                        startDestination = NavigationDestination.CitySearch.cityRoute
                    ) {
                        addSearchDestination()
                        addWeatherDetailsDestination()
                    }
                }
                LaunchedEffect(key1 = navController) {
                    navigator.setNavController(navController)
                }
            }
        }
    }
}
