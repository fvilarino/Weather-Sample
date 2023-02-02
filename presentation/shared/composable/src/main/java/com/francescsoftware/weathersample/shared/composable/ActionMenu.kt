package com.francescsoftware.weathersample.shared.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.styles.WidgetPreviews

sealed interface ActionMenuItem {
    val title: String
    val onClick: () -> Unit

    sealed interface IconMenuItem : ActionMenuItem {
        val icon: ImageVector
        val contentDescription: String?

        data class AlwaysShown(
            override val title: String,
            override val contentDescription: String?,
            override val onClick: () -> Unit,
            override val icon: ImageVector,
        ) : IconMenuItem

        data class ShownIfRoom(
            override val title: String,
            override val contentDescription: String?,
            override val onClick: () -> Unit,
            override val icon: ImageVector,
        ) : IconMenuItem
    }

    data class NeverShown(
        override val title: String,
        override val onClick: () -> Unit,
    ) : ActionMenuItem
}

@Composable
fun ActionsMenu(
    items: List<ActionMenuItem>,
    isOpen: Boolean,
    onToggleOverflow: () -> Unit,
    maxVisibleItems: Int = 3,
) {
    val menuItems = remember(
        key1 = items,
        key2 = maxVisibleItems,
    ) {
        splitMenuItems(items, maxVisibleItems)
    }

    menuItems.alwaysShownItems.forEach { item ->
        IconButton(onClick = item.onClick) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.contentDescription,
            )
        }
    }

    if (menuItems.overflowItems.isNotEmpty()) {
        IconButton(onClick = onToggleOverflow) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Overflow",
            )
        }
        DropdownMenu(
            expanded = isOpen,
            onDismissRequest = onToggleOverflow,
        ) {
            menuItems.overflowItems.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(item.title)
                    },
                    onClick = item.onClick
                )
            }
        }
    }
}

private data class MenuItems(
    val alwaysShownItems: List<ActionMenuItem.IconMenuItem>,
    val overflowItems: List<ActionMenuItem>,
)

private fun splitMenuItems(
    items: List<ActionMenuItem>,
    maxVisibleItems: Int,
): MenuItems {
    val alwaysShownItems: MutableList<ActionMenuItem.IconMenuItem> =
        items.filterIsInstance<ActionMenuItem.IconMenuItem.AlwaysShown>().toMutableList()
    val ifRoomItems: MutableList<ActionMenuItem.IconMenuItem> =
        items.filterIsInstance<ActionMenuItem.IconMenuItem.ShownIfRoom>().toMutableList()
    val overflowItems = items.filterIsInstance<ActionMenuItem.NeverShown>()

    val hasOverflow = overflowItems.isNotEmpty() ||
        (alwaysShownItems.size + ifRoomItems.size - 1) > maxVisibleItems
    val usedSlots = alwaysShownItems.size + (if (hasOverflow) 1 else 0)
    val availableSlots = maxVisibleItems - usedSlots
    if (availableSlots > 0 && ifRoomItems.isNotEmpty()) {
        val visible = ifRoomItems.subList(0, availableSlots.coerceAtMost(ifRoomItems.size))
        alwaysShownItems.addAll(visible)
        ifRoomItems.removeAll(visible)
    }

    return MenuItems(
        alwaysShownItems = alwaysShownItems,
        overflowItems = ifRoomItems + overflowItems,
    )
}

@WidgetPreviews
@Composable
internal fun ActionMenuPreview(
    @PreviewParameter(ActionMenuParameterProvider::class) items: List<ActionMenuItem>
) {
    WeatherSampleTheme {
        var menuOpen by remember {
            mutableStateOf(false)
        }
        val numAlwaysShown = items.count { item -> item is ActionMenuItem.IconMenuItem.AlwaysShown }
        val numIfRoom = items.count { item -> item is ActionMenuItem.IconMenuItem.ShownIfRoom }
        val numOverflow = items.count { item -> item is ActionMenuItem.NeverShown }
        val label = "Always: $numAlwaysShown Room: $numIfRoom Over: $numOverflow"
        TopAppBar(
            title = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            actions = {
                ActionsMenu(
                    items = items,
                    isOpen = menuOpen,
                    onToggleOverflow = { menuOpen = !menuOpen },
                    maxVisibleItems = 3
                )
            }
        )
    }
}

private class ActionMenuParameterProvider : PreviewParameterProvider<List<ActionMenuItem>> {
    override val values: Sequence<List<ActionMenuItem>>
        get() = sequenceOf(
            listOf(
                ActionMenuItem.IconMenuItem.AlwaysShown(
                    title = "Search",
                    onClick = {},
                    icon = Icons.Default.Search,
                    contentDescription = null,
                ),
                ActionMenuItem.IconMenuItem.AlwaysShown(
                    title = "Favorite",
                    onClick = {},
                    icon = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                ),
                ActionMenuItem.IconMenuItem.AlwaysShown(
                    title = "Star",
                    onClick = {},
                    icon = Icons.Default.Star,
                    contentDescription = null,
                ),
                ActionMenuItem.IconMenuItem.ShownIfRoom(
                    title = "Refresh",
                    onClick = {},
                    icon = Icons.Default.Refresh,
                    contentDescription = null,
                ),
                ActionMenuItem.NeverShown(
                    title = "Settings",
                    onClick = {},
                ),
                ActionMenuItem.NeverShown(
                    title = "About",
                    onClick = {},
                ),
            ),
            listOf(
                ActionMenuItem.IconMenuItem.AlwaysShown(
                    title = "Search",
                    onClick = {},
                    icon = Icons.Default.Search,
                    contentDescription = null,
                ),
                ActionMenuItem.IconMenuItem.AlwaysShown(
                    title = "Favorite",
                    onClick = {},
                    icon = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                ),
                ActionMenuItem.IconMenuItem.ShownIfRoom(
                    title = "Star",
                    onClick = {},
                    icon = Icons.Default.Star,
                    contentDescription = null,
                ),
                ActionMenuItem.IconMenuItem.ShownIfRoom(
                    title = "Refresh",
                    onClick = {},
                    icon = Icons.Default.Refresh,
                    contentDescription = null,
                ),
                ActionMenuItem.NeverShown(
                    title = "Settings",
                    onClick = {},
                ),
                ActionMenuItem.NeverShown(
                    title = "About",
                    onClick = {},
                ),
            ),
            listOf(
                ActionMenuItem.IconMenuItem.AlwaysShown(
                    title = "Search",
                    onClick = {},
                    icon = Icons.Default.Search,
                    contentDescription = null,
                ),
                ActionMenuItem.IconMenuItem.ShownIfRoom(
                    title = "Favorite",
                    onClick = {},
                    icon = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                ),
                ActionMenuItem.IconMenuItem.ShownIfRoom(
                    title = "Star",
                    onClick = {},
                    icon = Icons.Default.Star,
                    contentDescription = null,
                ),
                ActionMenuItem.IconMenuItem.ShownIfRoom(
                    title = "Refresh",
                    onClick = {},
                    icon = Icons.Default.Refresh,
                    contentDescription = null,
                ),
                ActionMenuItem.NeverShown(
                    title = "Settings",
                    onClick = {},
                ),
                ActionMenuItem.NeverShown(
                    title = "About",
                    onClick = {},
                ),
            ),
            listOf(
                ActionMenuItem.IconMenuItem.ShownIfRoom(
                    title = "Search",
                    onClick = {},
                    icon = Icons.Default.Search,
                    contentDescription = null,
                ),
                ActionMenuItem.IconMenuItem.ShownIfRoom(
                    title = "Favorite",
                    onClick = {},
                    icon = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                ),
                ActionMenuItem.IconMenuItem.ShownIfRoom(
                    title = "Star",
                    onClick = {},
                    icon = Icons.Default.Star,
                    contentDescription = null,
                ),
                ActionMenuItem.IconMenuItem.ShownIfRoom(
                    title = "Refresh",
                    onClick = {},
                    icon = Icons.Default.Refresh,
                    contentDescription = null,
                ),
                ActionMenuItem.NeverShown(
                    title = "Settings",
                    onClick = {},
                ),
                ActionMenuItem.NeverShown(
                    title = "About",
                    onClick = {},
                ),
            ),
            listOf(
                ActionMenuItem.IconMenuItem.ShownIfRoom(
                    title = "Search",
                    onClick = {},
                    icon = Icons.Default.Search,
                    contentDescription = null,
                ),
                ActionMenuItem.IconMenuItem.ShownIfRoom(
                    title = "Favorite",
                    onClick = {},
                    icon = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                ),
            ),
            listOf(
                ActionMenuItem.NeverShown(
                    title = "Search",
                    onClick = {},
                ),
                ActionMenuItem.NeverShown(
                    title = "Favorite",
                    onClick = {},
                ),
            ),
        )
}
