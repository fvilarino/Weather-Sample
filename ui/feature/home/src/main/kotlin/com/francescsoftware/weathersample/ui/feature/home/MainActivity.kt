package com.francescsoftware.weathersample.ui.feature.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.francescsoftware.weathersample.core.connectivity.api.ConnectivityMonitor
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/** {@inheritDoc} */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var connectivityMonitor: ConnectivityMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            val darkTheme = isSystemInDarkTheme()

            DisposableEffect(systemUiController, darkTheme) {
                systemUiController.systemBarsDarkContentEnabled = !darkTheme
                onDispose {}
            }

            WeatherApp(
                connectivityMonitor = connectivityMonitor,
            )
        }
    }
}
