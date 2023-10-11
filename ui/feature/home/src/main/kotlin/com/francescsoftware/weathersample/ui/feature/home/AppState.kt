package com.francescsoftware.weathersample.ui.feature.home

import android.app.Activity
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.francescsoftware.weathersample.core.connectivity.api.ConnectivityMonitor
import com.francescsoftware.weathersample.core.connectivity.api.ConnectivityStatus
import com.francescsoftware.weathersample.ui.shared.deviceclass.DeviceClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

private const val ConnectivityGracePeriodMillis = 5_000L

internal class AppState(
    val deviceClass: DeviceClass,
    val connectivityMonitor: ConnectivityMonitor,
    val scope: CoroutineScope,
) {
    val hasBottomNavBar: Boolean
        get() = deviceClass.hasBottomNavBar

    val hasNavRail: Boolean
        get() = !hasBottomNavBar

    val isConnected: StateFlow<Boolean> = connectivityMonitor.connectedStatus
        .map { state -> state == ConnectivityStatus.Connected }
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(ConnectivityGracePeriodMillis),
            initialValue = true,
        )

    private val DeviceClass.hasBottomNavBar: Boolean
        get() = this != DeviceClass.Expanded
}

@Composable
internal fun rememberAppState(
    connectivityMonitor: ConnectivityMonitor,
    navHostController: NavHostController = rememberNavController(),
    scope: CoroutineScope = rememberCoroutineScope(),
): AppState {
    val windowSizeClass = calculateWindowSizeClass(LocalContext.current as Activity)
    val deviceClass = DeviceClass.fromWindowSizeClass(windowSizeClass = windowSizeClass)
    return remember(key1 = navHostController, key2 = deviceClass) {
        AppState(
            deviceClass = deviceClass,
            connectivityMonitor = connectivityMonitor,
            scope = scope,
        )
    }
}
