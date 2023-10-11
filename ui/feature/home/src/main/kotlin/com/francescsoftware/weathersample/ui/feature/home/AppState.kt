package com.francescsoftware.weathersample.ui.feature.home

import android.app.Activity
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.francescsoftware.weathersample.core.connectivity.api.ConnectivityMonitor
import com.francescsoftware.weathersample.core.connectivity.api.ConnectivityStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

private const val ConnectivityGracePeriodMillis = 5_000L

private enum class NavigationType {
    BottomNav,
    NavRail;

    companion object {
        fun fromWindowSizeClass(windowSizeClass: WindowSizeClass): NavigationType = when {
            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact -> BottomNav
            windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact -> BottomNav
            else -> NavRail
        }
    }
}

internal class AppState(
    private val windowSizeClass: WindowSizeClass,
    connectivityMonitor: ConnectivityMonitor,
    scope: CoroutineScope,
) {
    private val navigationType: NavigationType
        get() = NavigationType.fromWindowSizeClass(windowSizeClass)

    val hasBottomNavBar: Boolean
        get() = navigationType == NavigationType.BottomNav

    val hasNavRail: Boolean
        get() = navigationType == NavigationType.NavRail

    val isConnected: StateFlow<Boolean> = connectivityMonitor.connectedStatus
        .map { state -> state == ConnectivityStatus.Connected }
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(ConnectivityGracePeriodMillis),
            initialValue = true,
        )
}

@Composable
internal fun rememberAppState(
    connectivityMonitor: ConnectivityMonitor,
    scope: CoroutineScope = rememberCoroutineScope(),
): AppState {
    val windowSizeClass = calculateWindowSizeClass(LocalContext.current as Activity)
    return remember(key1 = windowSizeClass, key2 = connectivityMonitor, key3 = scope) {
        AppState(
            windowSizeClass = windowSizeClass,
            connectivityMonitor = connectivityMonitor,
            scope = scope,
        )
    }
}
