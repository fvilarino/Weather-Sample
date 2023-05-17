package com.francescsoftware.weathersample.ui.feature.landing

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.francescsoftware.weathersample.ui.shared.route.RootNavigationDestination
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun NavRail(
    items: ImmutableList<RootNavigationDestination>,
    currentDestination: NavDestination?,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(
        modifier = modifier,
    ) {
        items.forEach { destination ->
            val content = destination.rootDestination.bottomNavContent
            NavigationRailItem(
                selected = currentDestination?.hierarchy?.any { navDestination ->
                    navDestination.route == destination.navGraphRoute
                } == true,
                icon = {
                    Icon(
                        imageVector = content.icon,
                        contentDescription = stringResource(id = content.contentDescriptionId),
                    )
                },
                label = { Text(text = stringResource(id = content.labelId)) },
                onClick = { onClick(destination.navGraphRoute) },
            )
        }
    }
}
