package com.francescsoftware.weathersample.ui.feature.favorites.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.francescsoftware.weathersample.ui.feature.favorites.R
import com.francescsoftware.weathersample.ui.feature.favorites.presenter.FavoritesScreen
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.Crossfade
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.GenericMessage
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.ProgressIndicator
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.slack.circuit.codegen.annotations.CircuitInject

@CircuitInject(FavoritesScreen::class, ActivityScope::class)
@Composable
internal fun Favorites(
    state: FavoritesScreen.State,
    modifier: Modifier = Modifier,
) {
    val eventSink = state.eventSink

    FavoriteScreen(
        state = state,
        onDeleteClick = { city -> eventSink(FavoritesScreen.Event.DeleteFavorite(city)) },
        modifier = modifier,
    )
}

@Composable
internal fun FavoriteScreen(
    state: FavoritesScreen.State,
    onDeleteClick: (FavoritesScreen.City) -> Unit,
    modifier: Modifier = Modifier,
) {
    Crossfade(
        targetState = state.favorites,
        contentKey = { it::class },
        modifier = modifier,
        label = "favorite_state",
    ) { favoritesState ->
        when (favoritesState) {
            FavoritesScreen.FavoritesState.Loading -> ProgressIndicator(
                label = stringResource(id = R.string.loading_favorites),
                modifier = Modifier.fillMaxSize(),
            )

            FavoritesScreen.FavoritesState.NoFavorites -> GenericMessage(
                message = stringResource(id = R.string.no_favorites_available),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MarginDouble),
            )

            FavoritesScreen.FavoritesState.Error -> GenericMessage(
                message = stringResource(id = R.string.favorites_load_error),
                icon = Icons.Default.Warning,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MarginDouble),
            )

            is FavoritesScreen.FavoritesState.Loaded -> FavoritePager(
                state = favoritesState.pagerState,
                onDeleteClick = onDeleteClick,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
