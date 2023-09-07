package com.francescsoftware.weathersample.ui.feature.favorites.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.navDeepLink
import com.francescsoftware.weathersample.ui.shared.assets.R
import com.francescsoftware.weathersample.ui.shared.route.BottomNavContent
import com.francescsoftware.weathersample.ui.shared.route.BottomNavigationDestination
import com.francescsoftware.weathersample.ui.shared.route.DeeplinkScheme

private const val DeeplinkHost = "favorite"

/** Favorites screen destination */
internal object FavoritesDestination : BottomNavigationDestination {

    /** @{inheritDoc} */
    override val title: String
        @Composable
        get() = stringResource(id = R.string.app_name)

    /** @{inheritDoc} */
    override val bottomNavContent = BottomNavContent(
        labelId = R.string.favorite_bottom_nav,
        icon = Icons.Default.FavoriteBorder,
        contentDescriptionId = R.string.content_description_favorite_bottom_nav,
    )

    /** @{inheritDoc} */
    override val deeplinks = listOf(
        navDeepLink { uriPattern = "$DeeplinkScheme://$DeeplinkHost" },
    )

    /** The deeplink route for the city screen */
    override val route: String = "favorites"

    /** @{inheritDoc} */
    override fun isRoute(route: String?): Boolean = route == FavoritesDestination.route
}
