package com.francescsoftware.weathersample.ui.feature.home

import androidx.activity.compose.LocalActivity
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.francescsoftware.weathersample.core.connectivity.api.ConnectivityMonitor
import com.francescsoftware.weathersample.core.connectivity.api.ConnectivityStatus
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.AppTheme
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.GetPreferencesInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

private const val FlowSubscribedGracePeriodMillis = 5_000L

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

internal enum class SystemTheme {
    Undefined,
    FollowSystem,
    Light,
    Dark,
}

internal enum class DynamicColors {
    Undefined,
    InUse,
    NotInUse,
}

internal class AppState(
    private val windowSizeClass: WindowSizeClass?,
    connectivityMonitor: ConnectivityMonitor,
    preferencesInteractor: GetPreferencesInteractor,
    scope: CoroutineScope,
) {
    init {
        preferencesInteractor(Unit)
    }

    private val navigationType: NavigationType
        get() = windowSizeClass?.let { NavigationType.fromWindowSizeClass(it) } ?: NavigationType.BottomNav

    val hasBottomNavBar: Boolean
        get() = navigationType == NavigationType.BottomNav

    val hasNavRail: Boolean
        get() = navigationType == NavigationType.NavRail

    val appTheme: StateFlow<SystemTheme> = preferencesInteractor.stream
        .map {
            when (it.appTheme) {
                AppTheme.System -> SystemTheme.FollowSystem
                AppTheme.Light -> SystemTheme.Light
                AppTheme.Dark -> SystemTheme.Dark
            }
        }
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(FlowSubscribedGracePeriodMillis),
            initialValue = SystemTheme.Undefined,
        )

    val useDynamicColors: StateFlow<DynamicColors> = preferencesInteractor.stream
        .map {
            when (it.dynamicColor) {
                true -> DynamicColors.InUse
                false -> DynamicColors.NotInUse
            }
        }
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(FlowSubscribedGracePeriodMillis),
            initialValue = DynamicColors.Undefined,
        )

    val isConnected: StateFlow<Boolean> = connectivityMonitor.connectedStatus
        .map { state -> state == ConnectivityStatus.Connected }
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(FlowSubscribedGracePeriodMillis),
            initialValue = true,
        )
}

@Composable
internal fun rememberAppState(
    connectivityMonitor: ConnectivityMonitor,
    preferencesInteractor: GetPreferencesInteractor,
    scope: CoroutineScope = rememberCoroutineScope(),
): AppState {
    val windowSizeClass = LocalActivity.current?.let { calculateWindowSizeClass(it) }
    return remember(windowSizeClass, connectivityMonitor, preferencesInteractor, scope) {
        AppState(
            windowSizeClass = windowSizeClass,
            connectivityMonitor = connectivityMonitor,
            preferencesInteractor = preferencesInteractor,
            scope = scope,
        )
    }
}
