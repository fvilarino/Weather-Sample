package com.francescsoftware.weathersample.ui.feature.search.city.presenter

import androidx.compose.runtime.Composable
import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
object SearchScreen : Screen {
    data class State(
        val loading: Boolean,
    ) : CircuitUiState
}

@CircuitInject(SearchScreen::class, ActivityScope::class)
class SearchPresenter @Inject constructor(
) : Presenter<SearchScreen.State> {

    @Composable
    override fun present(): SearchScreen.State {
        return SearchScreen.State(true)
    }
}
