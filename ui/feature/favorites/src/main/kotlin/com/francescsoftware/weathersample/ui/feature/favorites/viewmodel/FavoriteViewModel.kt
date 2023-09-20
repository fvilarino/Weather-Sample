package com.francescsoftware.weathersample.ui.feature.favorites.viewmodel

import com.francescsoftware.weathersample.core.coroutines.CloseableCoroutineScope
import com.francescsoftware.weathersample.ui.feature.favorites.ui.City
import com.francescsoftware.weathersample.ui.feature.favorites.ui.FavoritePagerState
import com.francescsoftware.weathersample.ui.shared.mvi.Action
import com.francescsoftware.weathersample.ui.shared.mvi.MviViewModel
import com.francescsoftware.weathersample.ui.shared.mvi.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

internal enum class LoadState {
    Loading,
    Loaded,
    NoFavorites,
    Error,
}

internal data class FavoriteState(
    val loadState: LoadState,
    val pagerState: FavoritePagerState,
) : State {
    companion object {
        val initial = FavoriteState(
            loadState = LoadState.Loading,
            pagerState = FavoritePagerState(
                pages = persistentListOf(),
            ),
        )
    }
}

internal sealed interface FavoriteAction : Action {
    data object Load : FavoriteAction
    data object NoFavorites : FavoriteAction
    data object LoadError : FavoriteAction
    data class Loaded(val state: FavoritePagerState) : FavoriteAction
    data class DeleteFavorite(val city: City) : FavoriteAction
}

@HiltViewModel
internal class FavoriteViewModel @Inject constructor(
    closeableScope: CloseableCoroutineScope,
    favoriteWeatherMiddleware: FavoriteWeatherMiddleware,
    favoriteCityMiddleware: FavoriteCityMiddleware,
    reducer: FavoriteReducer,
) : MviViewModel<FavoriteState, FavoriteAction>(
    closeableScope = closeableScope,
    reducer = reducer,
    middlewares = listOf(favoriteWeatherMiddleware, favoriteCityMiddleware),
    initialState = FavoriteState.initial,
) {
    fun onStart() {
        dispatch(FavoriteAction.Load)
    }

    fun onDeleteClick(city: City) {
        dispatch(FavoriteAction.DeleteFavorite(city))
    }
}
