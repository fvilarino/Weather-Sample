package com.francescsoftware.weathersample.ui.feature.settings.presenter

import androidx.compose.runtime.Stable
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.AppTheme
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
object SettingsScreen : Screen {
    @Stable
    data class State(
        val theme: AppTheme,
        val dynamicColors: Boolean,
        val dynamicColorsSupported: Boolean,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        data class AppThemeClick(val appTheme: AppTheme) : Event
        data class SetUseDynamicColors(val dynamicColors: Boolean) : Event
    }
}
