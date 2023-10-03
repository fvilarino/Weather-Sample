package com.francescsoftware.weathersample.ui.feature.home

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.slack.circuit.runtime.screen.Screen

@Composable
internal fun BottomNavBar(
    selectedScreen: Screen,
    onClick: (Screen) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        navigationDestinations.forEach { navItem ->
            val isSelected = navItem.screen == selectedScreen
            NavigationBarItem(
                selected = isSelected,
                onClick = { onClick(navItem.screen) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = stringResource(id = navItem.resourceId),
                    )
                },
                label = {
                    Text(
                        text = stringResource(navItem.resourceId),
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = NavigationColors.navigationIndicatorColor(),
                    selectedIconColor = NavigationColors.navigationSelectedItemColor(),
                    selectedTextColor = NavigationColors.navigationSelectedItemColor(),
                    unselectedIconColor = NavigationColors.navigationContentColor(),
                    unselectedTextColor = NavigationColors.navigationContentColor(),
                ),
            )
        }
    }
}
