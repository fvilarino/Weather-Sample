package com.francescsoftware.weathersample.ui.feature.favorites.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francescsoftware.weathersample.ui.feature.favorites.R
import com.francescsoftware.weathersample.ui.feature.favorites.viewmodel.FavoriteState
import com.francescsoftware.weathersample.ui.feature.favorites.viewmodel.FavoriteViewModel
import com.francescsoftware.weathersample.ui.feature.favorites.viewmodel.LoadState
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.GenericMessage
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.ProgressIndicator
import com.francescsoftware.weathersample.ui.shared.deviceclass.DeviceClass
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble

@Composable
internal fun FavoriteScreen(
    viewModel: FavoriteViewModel,
    deviceClass: DeviceClass,
    modifier: Modifier = Modifier,
) {
    FavoriteScreen(
        state = viewModel.state,
        deviceClass = deviceClass,
        onDeleteClick = viewModel::onDeleteClick,
        modifier = modifier,
    )
}

@Composable
internal fun FavoriteScreen(
    state: FavoriteState,
    deviceClass: DeviceClass,
    onDeleteClick: (City) -> Unit,
    modifier: Modifier = Modifier,
) {
    Crossfade(
        targetState = state.loadState,
        modifier = modifier,
        label = "favorite_state",
    ) { loadState ->
        when (loadState) {
            LoadState.Loading -> ProgressIndicator(
                label = stringResource(id = R.string.loading_favorites),
                modifier = Modifier.fillMaxSize(),
            )

            LoadState.Loaded -> FavoritePager(
                state = state.pagerState,
                deviceClass = deviceClass,
                onDeleteClick = onDeleteClick,
                modifier = Modifier.fillMaxSize(),
            )

            LoadState.NoFavorites -> GenericMessage(
                message = stringResource(id = R.string.no_favorites_available),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MarginDouble)
            )

            LoadState.Error -> GenericMessage(
                message = stringResource(id = R.string.favorites_load_error),
                icon = Icons.Default.Warning,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MarginDouble)
            )
        }
    }
}
