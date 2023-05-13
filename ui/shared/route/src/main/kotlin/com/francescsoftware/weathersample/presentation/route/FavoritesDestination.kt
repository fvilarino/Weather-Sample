package com.francescsoftware.weathersample.presentation.route

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import com.francescsoftware.weathersample.shared.assets.R

/** City screen Destination */
object FavoritesDestination : NavigationDestination {

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
    override fun isRoute(route: String?): Boolean = route == this.route
}
