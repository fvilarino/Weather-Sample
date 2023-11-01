package com.francescsoftware.weathersample.ui.feature.home

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.francescsoftware.weathersample.core.connectivity.api.ConnectivityMonitor
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.GetPreferencesInteractor
import com.francescsoftware.weathersample.ui.feature.home.di.ActivityComponent
import com.francescsoftware.weathersample.ui.feature.home.di.ActivityComponentFactoryProvider
import com.francescsoftware.weathersample.ui.shared.composable.common.composition.LocalWindowSizeClass
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.overlay.ContentWithOverlays
import com.slack.circuit.overlay.rememberOverlayHost
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
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
    lateinit var preferencesInteractor: GetPreferencesInteractor

    @Inject
    internal lateinit var circuit: Circuit

    private lateinit var activityComponent: ActivityComponent
    private var settingsLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val factory = (applicationContext as ActivityComponentFactoryProvider).getActivityComponentFactory()
        activityComponent = factory.create(this)
        activityComponent.inject(this)

        handleSplashScreen()

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
            CircuitCompositionLocals(circuit) {
                val overlayHost = rememberOverlayHost()
                ContentWithOverlays(
                    overlayHost = overlayHost,
                ) {
                    CompositionLocalProvider(
                        LocalWindowSizeClass provides calculateWindowSizeClass(this),
                    ) {
                        WeatherApp(
                            connectivityMonitor = connectivityMonitor,
                            preferencesInteractor = preferencesInteractor,
                        )
                    }
                }
            }
        }
    }

    private fun handleSplashScreen() {
        val splashScreen = installSplashScreen()
        preferencesInteractor(Unit)
        lifecycleScope.launch {
            preferencesInteractor.stream.first()
            settingsLoaded = true
        }
        splashScreen.setKeepOnScreenCondition {
            !settingsLoaded
        }
    }
}
