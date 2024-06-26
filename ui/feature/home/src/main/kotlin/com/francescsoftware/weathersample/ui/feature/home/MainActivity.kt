package com.francescsoftware.weathersample.ui.feature.home

import android.app.UiModeManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.core.content.getSystemService
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.francescsoftware.weathersample.core.connectivity.api.ConnectivityMonitor
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.AppTheme
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.GetPreferencesInteractor
import com.francescsoftware.weathersample.ui.feature.home.di.ActivityComponent
import com.francescsoftware.weathersample.ui.feature.home.di.ActivityComponentFactoryProvider
import com.francescsoftware.weathersample.ui.shared.composable.common.composition.LocalWindowSizeClass
import com.francescsoftware.weathersample.ui.shared.deeplink.DeeplinkParser
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.overlay.ContentWithOverlays
import com.slack.circuit.overlay.rememberOverlayHost
import kotlinx.coroutines.flow.map
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

    @Inject
    internal lateinit var deeplinkParser: DeeplinkParser

    private lateinit var activityComponent: ActivityComponent
    private var settingsLoaded = false

    private val uiModeManager: UiModeManager?
        get() = getSystemService<UiModeManager>()

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
                            deeplinkParser = deeplinkParser,
                            preferencesInteractor = preferencesInteractor,
                        )
                    }
                }
            }
        }
        deeplinkParser.parse(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        deeplinkParser.parse(intent)
    }

    private fun handleSplashScreen() {
        val splashScreen = installSplashScreen()
        preferencesInteractor(Unit)
        lifecycleScope.launch {
            preferencesInteractor.stream
                .map { settings -> settings.appTheme }
                .collect { theme ->
                    val uiTheme = theme.toUiTheme
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        uiModeManager?.setApplicationNightMode(uiTheme)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(uiTheme)
                    }
                    settingsLoaded = true
                }
        }
        splashScreen.setKeepOnScreenCondition {
            !settingsLoaded
        }
    }

    private val AppTheme.toUiTheme: Int
        get() = when (this) {
            AppTheme.System -> UiModeManager.MODE_NIGHT_AUTO
            AppTheme.Light -> UiModeManager.MODE_NIGHT_NO
            AppTheme.Dark -> UiModeManager.MODE_NIGHT_YES
        }
}
