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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.francescsoftware.weathersample.presentation.feature.navigator.NavigationDestination
import com.francescsoftware.weathersample.presentation.feature.navigator.Navigator
import com.francescsoftware.weathersample.presentation.feature.search.CityScreen
import com.francescsoftware.weathersample.presentation.feature.search.CityViewModel
import com.francescsoftware.weathersample.presentation.feature.weather.SelectedCity
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
                navigator.setNavController(navController)
                val state = navigator.currentDestination.collectAsState()
                Scaffold(
                    topBar = {
                        TopAppBar {
                            if (state.value.icon != 0) {
                                IconButton(onClick = { navigator.onBackClick() }) {
                                    Icon(
                                        painter = painterResource(id = state.value.icon),
                                        contentDescription = null,
                                    )
                                }
                            }
                            Text(
                                text = state.value.title,
                                modifier = Modifier.padding(start = MarginDouble)
                            )
                        }
                    },
                ) {
                    NavHost(navController, startDestination = NavigationDestination.CitySearch.route) {
                        composable(route = NavigationDestination.CitySearch.route) {
                            val cityViewModel: CityViewModel = hiltNavGraphViewModel()
                            CityScreen(cityViewModel)
                        }
                        composable(NavigationDestination.Weather.route) {
                            val weatherViewModel: WeatherViewModel = hiltNavGraphViewModel()
                            val city = navController.previousBackStackEntry?.arguments?.getParcelable<SelectedCity>("city")
                                ?: throw IllegalStateException("No arguments available")
                            WeatherScreen(weatherViewModel, city)
                        }
                    }
                }
            }
        }
    }
}
