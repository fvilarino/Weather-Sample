package com.francescsoftware.weathersample.ui.feature.home

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.francescsoftware.weathersample.core.connectivity.api.ConnectivityMonitor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private val lightScrim = Color.argb(0xE6, 0xFF, 0xFF, 0xFF)
private val darkScrim = Color.argb(0x80, 0x1B, 0x1B, 0x1B)

/** {@inheritDoc} */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var connectivityMonitor: ConnectivityMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        enableEdgeToEdge()
        setContent {
            val darkTheme = isSystemInDarkTheme()
            DisposableEffect(darkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                    ) { darkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim,
                        darkScrim,
                    ) { darkTheme },
                )
                onDispose {}
            }
            WeatherApp(
                connectivityMonitor = connectivityMonitor,
            )
        }
    }
}
