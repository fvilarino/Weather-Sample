package com.francescsoftware.weathersample.ui.feature.favorites.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.francescsoftware.weathersample.ui.feature.favorites.presenter.FavoritesScreen
import com.francescsoftware.weathersample.ui.shared.navigation.NavigationDestination
import com.slack.circuit.runtime.screen.Screen
import com.francescsoftware.weathersample.ui.shared.assets.R as assetsR

@Stable
object FavoritesDestination : NavigationDestination {
    override val rootScreen: Screen
        get() = FavoritesScreen

    override val navItemLabel: Int
        get() = assetsR.string.favorite_bottom_nav

    override val navItemIcon: ImageVector
        get() = Icons.Default.FavoriteBorder

    override val navItemContentDescription: Int
        get() = assetsR.string.content_description_favorite_bottom_nav

    @Composable
    override fun actionBarLabel(screen: Screen): String = when (screen) {
        FavoritesScreen -> stringResource(id = assetsR.string.app_name)
        else -> error("Unknown screen $screen")
    }
}
