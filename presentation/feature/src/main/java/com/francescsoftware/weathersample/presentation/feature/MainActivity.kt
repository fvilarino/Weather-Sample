package com.francescsoftware.weathersample.presentation.feature

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.francescsoftware.weathersample.presentation.feature.navigator.NavigationDestination
import com.francescsoftware.weathersample.presentation.feature.navigator.Navigator
import com.francescsoftware.weathersample.presentation.feature.search.CityScreen
import com.francescsoftware.weathersample.presentation.feature.search.CityViewModel
import com.francescsoftware.weathersample.presentation.feature.weather.WeatherScreen
import com.francescsoftware.weathersample.presentation.feature.weather.WeatherViewModel
import com.francescsoftware.weathersample.styles.MarginDouble
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
                        TopAppBar {
                            if (currentDestination.iconId != 0) {
                                IconButton(onClick = { navigator.onBackClick() }) {
                                    Icon(
                                        painter = painterResource(id = currentDestination.iconId),
                                        contentDescription = null,
                                    )
                                }
                            }
                            Text(
                                text = stringResource(id = currentDestination.titleId),
                                modifier = Modifier.padding(start = MarginDouble)
                            )
                        }
                    },
                ) {
                    NavHost(navController, startDestination = NavigationDestination.CitySearch.getRoute()) {
                        composable(
                            route = NavigationDestination.CitySearch.getRoute()
                        ) {
                            val cityViewModel: CityViewModel = hiltViewModel()
                            CityScreen(cityViewModel)
                        }
                        composable(
                            route = NavigationDestination.Weather.getRoute(),
                        ) {
                            val weatherViewModel: WeatherViewModel = hiltViewModel()
                            WeatherScreen(weatherViewModel)
                        }
                    }
                }
                LaunchedEffect(key1 = navController) {
                    navigator.setNavController(navController)
                }
            }
        }
    }
}
