package com.francescsoftware.weathersample.ui.feature.favorites.presenter

import androidx.compose.runtime.Composable
import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
object FavoritesScreen : Screen {
    data class State(
        val loading: Boolean,
    ) : CircuitUiState
}

@CircuitInject(FavoritesScreen::class, ActivityScope::class)
class FavoritesPresenter @Inject constructor(
) : Presenter<FavoritesScreen.State> {

    @Composable
    override fun present(): FavoritesScreen.State {
        return FavoritesScreen.State(true)
    }
}
