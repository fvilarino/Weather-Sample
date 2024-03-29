package com.francescsoftware.weathersample.ui.feature.favorites.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.francescsoftware.weathersample.domain.interactor.city.api.DeleteFavoriteCityInteractor
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.presenter.Presenter
import kotlinx.coroutines.launch
import javax.inject.Inject

@CircuitInject(FavoritesScreen::class, ActivityScope::class)
class FavoritesPresenter @Inject constructor(
    private val favoritesLoader: FavoritesLoader,
    private val deleteFavoriteCityInteractor: DeleteFavoriteCityInteractor,
) : Presenter<FavoritesScreen.State> {

    @Composable
    override fun present(): FavoritesScreen.State {
        val scope = rememberCoroutineScope()
        val favorites by favoritesLoader.favoritesState.collectAsRetainedState(
            initial = FavoritesScreen.FavoritesState.Loading,
        )

        return FavoritesScreen.State(
            favorites = favorites,
            eventSink = { event ->
                when (event) {
                    is FavoritesScreen.Event.DeleteFavorite -> scope.launch {
                        deleteFavorite(event.city)
                    }
                }
            },
        )
    }

    private suspend fun deleteFavorite(city: FavoritesScreen.City) {
        deleteFavoriteCityInteractor(
            DeleteFavoriteCityInteractor.Params(
                city.cityId,
            ),
        )
    }
}
