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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object CitySearchDestination : NavigationDestination {
    enum class Actions {
        About,
    }

    data class ActionsState(
        val about: Boolean = false,
    )

    override val titleId: Int = com.francescsoftware.weathersample.shared.assets.R.string.app_name
    override val iconId: Int = 0
    override val iconContentDescriptionId: Int = 0

    const val cityRoute: String = "city_search"

    private val _actionsState = MutableStateFlow(ActionsState())
    val actionsState: StateFlow<ActionsState> = _actionsState.asStateFlow()

    fun getRoute(): String = cityRoute
    override fun isRoute(route: String?): Boolean = route == cityRoute

    @Composable
    override fun TopBarActions() {
        var menuExpanded by rememberSaveable {
            mutableStateOf(false)
        }

        ActionsMenu(
            items = listOf(
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
