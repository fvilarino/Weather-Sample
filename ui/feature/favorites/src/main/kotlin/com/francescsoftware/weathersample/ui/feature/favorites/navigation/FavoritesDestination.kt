package com.francescsoftware.weathersample.ui.feature.favorites.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import com.francescsoftware.weathersample.ui.shared.assets.R
import com.francescsoftware.weathersample.ui.shared.route.BottomNavContent
import com.francescsoftware.weathersample.ui.shared.route.BottomNavigationDestination

/** Favorites screen Destination */
internal object FavoritesDestination : BottomNavigationDestination {

    /** @{inheritDoc} */
    override val titleId: Int = R.string.app_name

    /** @{inheritDoc} */
    override val iconId: Int = 0

    /** @{inheritDoc} */
    override val iconContentDescriptionId: Int = 0

    /** @{inheritDoc} */
    override val bottomNavContent = BottomNavContent(
        labelId = R.string.favorite_bottom_nav,
        icon = Icons.Default.FavoriteBorder,
        contentDescriptionId = R.string.content_description_search_bottom_nav,
    )

    /** The deeplink route for the city screen */
    override val route: String = "favorites"

    /** @{inheritDoc} */
    override fun isRoute(route: String?): Boolean = route == FavoritesDestination.route
}
