package com.francescsoftware.weathersample.ui.feature.favorites.viewmodel

import com.francescsoftware.weathersample.shared.mvi.Reducer
import javax.inject.Inject

internal class FavoriteReducer @Inject constructor() : Reducer<FavoriteState, FavoriteAction> {
    override fun reduce(
        state: FavoriteState,
        action: FavoriteAction,
    ): FavoriteState = when (action) {
        FavoriteAction.Load -> state.copy(
            loadState = LoadState.Loading,
        )

        FavoriteAction.NoFavorites -> state.copy(
            loadState = LoadState.NoFavorites,
        )

        FavoriteAction.LoadError -> state.copy(
            loadState = LoadState.Error,
        )

        is FavoriteAction.Loaded -> state.copy(
            loadState = LoadState.Loaded,
            pagerState = action.state,
        )

        is FavoriteAction.DeleteFavorite -> state
    }
}
