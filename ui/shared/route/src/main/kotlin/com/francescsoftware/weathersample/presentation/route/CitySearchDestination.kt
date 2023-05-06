package com.francescsoftware.weathersample.presentation.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.francescsoftware.weathersample.shared.assets.R
import com.francescsoftware.weathersample.shared.composable.ActionMenuItem
import com.francescsoftware.weathersample.shared.composable.ActionsMenu
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
    override val titleId: Int = com.francescsoftware.weathersample.shared.assets.R.string.app_name

    /** @{inheritDoc} */
    override val iconId: Int = 0

    /** @{inheritDoc} */
    override val iconContentDescriptionId: Int = 0

    /** The deeplink route for the city screen */
    const val cityRoute: String = "city_search"

    private val _actionsState = MutableStateFlow(ActionsState())

    /** Flow of [ActionsState] representing the clicked actions */
    val actionsState: StateFlow<ActionsState> = _actionsState.asStateFlow()

    /**
     * The route to navigate to the city screen
     *
     * @return a string representing the city screen route
     */
    fun getRoute(): String = cityRoute

    /** @{inheritDoc} */
    override fun isRoute(route: String?): Boolean = route == cityRoute

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
