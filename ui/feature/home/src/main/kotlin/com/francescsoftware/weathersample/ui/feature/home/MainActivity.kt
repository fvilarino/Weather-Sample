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
import com.francescsoftware.weathersample.ui.feature.home.di.ActivityComponent
import com.francescsoftware.weathersample.ui.feature.home.di.ActivityComponentFactoryProvider
import com.slack.circuit.foundation.Circuit
import javax.inject.Inject

@Suppress("MagicNumber")
private val lightScrim = Color.argb(0xE6, 0xFF, 0xFF, 0xFF)

@Suppress("MagicNumber")
private val darkScrim = Color.argb(0x80, 0x1B, 0x1B, 0x1B)

/** {@inheritDoc} */
class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var connectivityMonitor: ConnectivityMonitor

    @Inject
    internal lateinit var circuit: Circuit

    private lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        val factory = (applicationContext as ActivityComponentFactoryProvider).getActivityComponentFactory()
        activityComponent = factory.create(this)
        activityComponent.inject(this)
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
