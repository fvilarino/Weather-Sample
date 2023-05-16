package com.francescsoftware.weathersample.ui.shared.route

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.francescsoftware.weathersample.ui.shared.assets.R
import com.francescsoftware.weathersample.ui.shared.composable.common.ActionMenuItem
import com.francescsoftware.weathersample.ui.shared.composable.common.ActionsMenu
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/** City screen Destination */
object CitySearchDestination : NavigationDestination {
    /** Possible actions for the actions bar menu on the city screen */
    enum class Actions {
        About,
    }

    /**
     * The selected actions
     *
     * @property about - when true the About action was clicked
     */
    data class ActionsState(
        val about: Boolean = false,
    )

    /** @{inheritDoc} */
    override val titleId: Int = R.string.app_name

    /** @{inheritDoc} */
    override val iconId: Int = 0

    /** @{inheritDoc} */
    override val iconContentDescriptionId: Int = 0

    /** @{inheritDoc} */
    override val bottomNavContent = BottomNavContent(
        labelId = R.string.search_bottom_nav,
        icon = Icons.Default.Search,
        contentDescriptionId = R.string.content_description_search_bottom_nav,
    )

    /** The deeplink route for the city screen */
    override val route: String = "city_search"

    private val _actionsState = MutableStateFlow(ActionsState())

    /** Flow of [ActionsState] representing the clicked actions */
    val actionsState: StateFlow<ActionsState> = _actionsState.asStateFlow()

    /** @{inheritDoc} */
    override fun isRoute(route: String?): Boolean = route == CitySearchDestination.route

    /** @{inheritDoc} */
    @Composable
    override fun TopBarActions() {
        var menuExpanded by rememberSaveable {
            mutableStateOf(false)
        }

        ActionsMenu(
            items = persistentListOf(
                ActionMenuItem.NeverShown(
                    title = stringResource(
                        id = R.string.action_item_about
                    ),
                    onClick = {
                        menuExpanded = false
                        _actionsState.update { state ->
                            state.copy(
                                about = true,
                            )
                        }
                    }
                )
            ),
            isOpen = menuExpanded,
            onToggleOverflow = { menuExpanded = !menuExpanded },
        )
    }

    /**
     * Consumes the action event
     *
     * @param action - the [Actions] to consume
     */
    fun consume(
        action: Actions
    ) {
        _actionsState.update { state ->
            when (action) {
                Actions.About -> state.copy(
                    about = false,
                )
            }
        }
    }
}
