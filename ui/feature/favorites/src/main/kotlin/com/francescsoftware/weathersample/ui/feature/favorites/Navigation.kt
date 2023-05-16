package com.francescsoftware.weathersample.ui.feature.favorites

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.francescsoftware.weathersample.ui.feature.favorites.ui.FavoriteScreen
import com.francescsoftware.weathersample.ui.feature.favorites.viewmodel.FavoriteViewModel
import com.francescsoftware.weathersample.ui.shared.deviceclass.DeviceClass
import com.francescsoftware.weathersample.ui.shared.route.FavoritesDestination

/**
 * Add Favorites destination to Nav Graph
 *
 * @param deviceClass - The [DeviceClass] the composable is running on
 * @receiver The [NavGraphBuilder] this destination should be added to.
 */
fun NavGraphBuilder.addFavoritesDestination(
    deviceClass: DeviceClass,
) {
    composable(
        route = FavoritesDestination.route,
    ) {
        val favoriteViewModel: FavoriteViewModel = hiltViewModel()
        FavoriteScreen(
            viewModel = favoriteViewModel,
            deviceClass = deviceClass,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
