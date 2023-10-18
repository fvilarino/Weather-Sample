package com.francescsoftware.weathersample.ui.feature.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.AppTheme
import com.francescsoftware.weathersample.ui.feature.settings.R
import com.francescsoftware.weathersample.ui.feature.settings.presenter.SettingsScreen
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginSingle
import com.francescsoftware.weathersample.ui.shared.styles.PhonePreviews
import com.francescsoftware.weathersample.ui.shared.styles.TabletPreviews
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.slack.circuit.codegen.annotations.CircuitInject

private val MaxSettingsWidth = 460.dp

@CircuitInject(SettingsScreen::class, ActivityScope::class)
@Composable
internal fun Settings(
    state: SettingsScreen.State,
    modifier: Modifier = Modifier,
) {
    val eventSink = state.eventSink

    Settings(
        state = state,
        onThemeClick = { eventSink(SettingsScreen.Event.AppThemeClick(it)) },
        onDynamicColorChange = { eventSink(SettingsScreen.Event.SetUseDynamicColors(it)) },
        modifier = modifier,
    )
}

@Composable
private fun Settings(
    state: SettingsScreen.State,
    onThemeClick: (AppTheme) -> Unit,
    onDynamicColorChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.widthIn(max = MaxSettingsWidth).align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MarginSingle),
        ) {
            ThemePreference(
                selectedTheme = state.theme,
                onClick = onThemeClick,
                modifier = Modifier.fillMaxWidth().padding(horizontal = MarginDouble),
            )
            SwitchPreference(
                title = stringResource(id = R.string.use_dynamic_theming),
                description = stringResource(id = R.string.use_colors_from_your_wallpapers),
                isChecked = state.dynamicColors,
                enabled = state.dynamicColorsSupported,
                onCheckChange = onDynamicColorChange,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@PhonePreviews
@TabletPreviews
@Composable
private fun PreviewSettings() {
    WeatherSampleTheme {
        var theme by remember { mutableStateOf(AppTheme.System) }
        var isDynamic by remember { mutableStateOf(false) }
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            Settings(
                state = SettingsScreen.State(
                    theme = theme,
                    dynamicColors = isDynamic,
                    dynamicColorsSupported = true,
                    eventSink = {},
                ),
                onThemeClick = { theme = it },
                onDynamicColorChange = { isDynamic = it },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MarginDouble),
            )
        }
    }
}
