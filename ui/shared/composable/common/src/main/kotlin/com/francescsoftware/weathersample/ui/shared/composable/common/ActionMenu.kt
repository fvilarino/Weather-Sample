@file:Suppress("MatchingDeclarationName")

package com.francescsoftware.weathersample.ui.shared.composable.common

import androidx.compose.foundation.layout.Row
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
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/** Base class for the menu options on a [TopAppBar] */
sealed interface ActionMenuItem {
    /** Label for this action menu item */
    val title: String

    /** Called when clicking on this action menu item */
    val onClick: () -> Unit

    /** [ActionMenuItem] that displays on the [TopAppBar] as an icon. */
    sealed interface IconMenuItem : ActionMenuItem {
        /** The icon to display for this action menu item */
        val icon: ImageVector

        /** The content description describing the [icon] */
        val contentDescription: String?

        /**
         * [ActionMenuItem] that is always displayed on the [TopAppBar]
         *
         * @property title title for the action menu option
         * @property contentDescription accessibility label for the action menu option
         * @property onClick called when the user clicks on this menu option
         * @property icon icon to display on the [TopAppBar]
         */
        data class AlwaysShown(
            override val title: String,
            override val contentDescription: String?,
            override val onClick: () -> Unit,
            override val icon: ImageVector,
        ) : IconMenuItem

        /**
         * [ActionMenuItem] that is displayed on the [TopAppBar] if there is enough room
         *
         * @property title title for the action menu option
         * @property contentDescription accessibility label for the action menu option
         * @property onClick called when the user clicks on this menu option
         * @property icon icon to display on the [TopAppBar]
         */
        data class ShownIfRoom(
            override val title: String,
            override val contentDescription: String?,
            override val onClick: () -> Unit,
            override val icon: ImageVector,
        ) : IconMenuItem
    }

    /**
     * [ActionMenuItem] that is displayed in the overflow drop down menu
     *
     * @property title title for the action menu option
     * @property onClick called when the user clicks on this menu option
     */
    data class NeverShown(
        override val title: String,
        override val onClick: () -> Unit,
    ) : ActionMenuItem
}

/**
 * Displays an actions menu.
 *
 * @param items options menu items to display
 * @param isOpen if true the dropdown is open, if false it's closed
 * @param onToggleOverflow called when tapping on the overflow button
 * @param modifier the [Modifier] to apply to this actions menu
 * @param maxVisibleItems maximum number of items to display as icons
 */
@Composable
fun ActionsMenu(
    items: ImmutableList<ActionMenuItem>,
    isOpen: Boolean,
    onToggleOverflow: () -> Unit,
    modifier: Modifier = Modifier,
    maxVisibleItems: Int = 3,
) {
    val menuItems = remember(
        key1 = items,
        key2 = maxVisibleItems,
    ) {
        splitMenuItems(items, maxVisibleItems)
    }

    Row(
        modifier = modifier
    ) {
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
        ifRoomItems.removeAll(visible.toSet())
    }

    return MenuItems(
        alwaysShownItems = alwaysShownItems,
        overflowItems = ifRoomItems + overflowItems,
    )
}

@WidgetPreviews
@Composable
private fun PreviewActionMenu(
    @PreviewParameter(ActionMenuParameterProvider::class) items: ImmutableList<ActionMenuItem>
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
            persistentListOf(
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
