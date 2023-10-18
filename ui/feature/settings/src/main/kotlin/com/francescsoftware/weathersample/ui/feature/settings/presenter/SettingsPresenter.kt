package com.francescsoftware.weathersample.ui.feature.settings.presenter

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.AppSettings
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.AppTheme
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.GetPreferencesInteractor
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.SetAppThemeInteractor
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.SetUseDynamicColorsInteractor
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.presenter.Presenter
import kotlinx.coroutines.launch
import javax.inject.Inject

@CircuitInject(SettingsScreen::class, ActivityScope::class)
class SettingsPresenter @Inject constructor(
    private val getPreferencesInteractor: GetPreferencesInteractor,
    private val setAppThemeInteractor: SetAppThemeInteractor,
    private val setUseDynamicColorsInteractor: SetUseDynamicColorsInteractor,
) : Presenter<SettingsScreen.State> {

    @Composable
    override fun present(): SettingsScreen.State {
        val preferences by getPreferencesInteractor.stream.collectAsRetainedState(
            initial = AppSettings(
                AppTheme.System,
                true,
            ),
        )
        val theme = preferences.appTheme
        val useDynamic = supportsDynamicColors() && preferences.dynamicColor
        val scope = rememberCoroutineScope()
        LaunchedEffect(key1 = getPreferencesInteractor) {
            getPreferencesInteractor(Unit)
        }
        fun eventSink(event: SettingsScreen.Event) {
            when (event) {
                is SettingsScreen.Event.AppThemeClick -> scope.launch {
                    setAppThemeInteractor(event.appTheme)
                }

                is SettingsScreen.Event.SetUseDynamicColors -> scope.launch {
                    setUseDynamicColorsInteractor(event.dynamicColors)
                }
            }
        }
        return SettingsScreen.State(
            theme = theme,
            dynamicColors = useDynamic,
            dynamicColorsSupported = supportsDynamicColors(),
            eventSink = ::eventSink,
        )
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
    private fun supportsDynamicColors() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}
