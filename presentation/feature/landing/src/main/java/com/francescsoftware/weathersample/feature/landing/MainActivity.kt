package com.francescsoftware.weathersample.feature.landing

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.francescsoftware.weathersample.feature.city.addSearchDestination
import com.francescsoftware.weathersample.feature.weather.addWeatherDetailsDestination
import com.francescsoftware.weathersample.presentation.route.NavigationDestination
import com.francescsoftware.weathersample.shared.composable.AppBar
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
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
                            title = stringResource(id = currentDestination.titleId),
                            iconId = currentDestination.iconId,
                            iconContentDescription = if (currentDestination.iconContentDescriptionId != 0) {
                                stringResource(id = currentDestination.iconContentDescriptionId)
                            } else {
                                null
                            },
                            onIconClick = navController::popBackStack,
                            modifier = Modifier.systemBarsPadding(),
                        )
                    },
                ) { paddingValues ->
                    NavHost(
                        navController,
                        startDestination = NavigationDestination.CitySearch.cityRoute,
                        modifier = Modifier.padding(paddingValues),
                    ) {
                        addSearchDestination(
                            onCityClick = { selectedCity ->
                                navController.navigate(NavigationDestination.Weather.getRoute(selectedCity))
                            }
                        )
                        addWeatherDetailsDestination()
                    }
                }
            }
        }
    }
}
