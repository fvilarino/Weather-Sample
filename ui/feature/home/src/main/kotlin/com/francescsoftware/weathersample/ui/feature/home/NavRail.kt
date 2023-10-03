package com.francescsoftware.weathersample.ui.feature.home

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.slack.circuit.runtime.screen.Screen

@Composable
internal fun NavRail(
    selectedScreen: Screen,
    onClick: (Screen) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(
        contentColor = NavigationColors.navigationContentColor(),
        modifier = modifier,
    ) {
        navigationDestinations.forEach { destination ->
            val selected = selectedScreen == destination.screen
            NavigationRailItem(
                selected = selected,
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = stringResource(id = destination.resourceId),
                    )
                },
                label = { Text(text = stringResource(id = destination.resourceId)) },
                colors = NavigationRailItemDefaults.colors(
                    indicatorColor = NavigationColors.navigationIndicatorColor(),
                    selectedIconColor = NavigationColors.navigationSelectedItemColor(),
                    selectedTextColor = NavigationColors.navigationSelectedItemColor(),
                    unselectedIconColor = NavigationColors.navigationContentColor(),
                    unselectedTextColor = NavigationColors.navigationContentColor(),
                ),
                onClick = { onClick(destination.screen) },
            )
        }
    }
}
