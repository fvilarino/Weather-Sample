package com.francescsoftware.weathersample.ui.feature.favorites.ui

import kotlinx.collections.immutable.ImmutableList

internal data class FavoritePagerState(
    val pages: ImmutableList<FavoriteCardState>,
)
