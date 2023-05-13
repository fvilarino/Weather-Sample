package com.francescsoftware.weathersample.feature.landing

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francescsoftware.weathersample.presentation.route.NavigationDestination
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun BottomNavBar(
    items: ImmutableList<NavigationDestination>,
    currentRoute: String?,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        items.forEach { destination ->
            val content = requireNotNull(destination.bottomNavContent)
            NavigationBarItem(
                selected = destination.isRoute(currentRoute),
                icon = {
                    Icon(
                        imageVector = content.icon,
                        contentDescription = stringResource(id = content.contentDescriptionId),
                    )
                },
                label = { Text(text = stringResource(id = content.labelId)) },
                onClick = { onClick(destination.route) },
            )
        }
    }
}
