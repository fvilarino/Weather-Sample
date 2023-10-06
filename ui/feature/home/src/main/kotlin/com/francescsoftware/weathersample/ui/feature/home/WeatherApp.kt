package com.francescsoftware.weathersample.ui.feature.home

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.francescsoftware.weathersample.core.connectivity.api.ConnectivityMonitor
import com.francescsoftware.weathersample.ui.feature.favorites.presenter.FavoritesScreen
import com.francescsoftware.weathersample.ui.feature.search.city.presenter.SearchScreen
import com.francescsoftware.weathersample.ui.feature.search.weather.presenter.WeatherScreen
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.AppBar
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.runtime.screen.Screen
import kotlinx.collections.immutable.persistentListOf
import com.francescsoftware.weathersample.ui.shared.assets.R as assetsR

sealed class BottomNavigationScreens(
    val screen: Screen,
    @StringRes val resourceId: Int,
    val icon: ImageVector,
) {
    data object Search : BottomNavigationScreens(
        screen = SearchScreen,
        resourceId = com.francescsoftware.weathersample.ui.shared.assets.R.string.search_bottom_nav,
        icon = Icons.Default.Search,
    )

    data object Favorites : BottomNavigationScreens(
        screen = FavoritesScreen,
        resourceId = com.francescsoftware.weathersample.ui.shared.assets.R.string.favorite_bottom_nav,
        icon = Icons.Default.FavoriteBorder,
    )
}

internal val navigationDestinations = persistentListOf(
    BottomNavigationScreens.Search,
    BottomNavigationScreens.Favorites,
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun WeatherApp(
    connectivityMonitor: ConnectivityMonitor,
    state: AppState = rememberAppState(
        connectivityMonitor = connectivityMonitor,
    ),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val isConnected by state.isConnected.collectAsStateWithLifecycle()
    val networkLostMessage = stringResource(id = R.string.network_connection_lost)
    val scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val backstack = rememberSaveableBackStack {
        push(SearchScreen)
    }
    val navigator = rememberCircuitNavigator(backstack)
    val rootScreen by remember(backstack) {
        derivedStateOf { backstack.last().screen }
    }
    val topScreen by remember(backstack) {
        derivedStateOf { backstack.first().screen }
    }
    val isAtRoot by remember(backstack) {
        derivedStateOf { backstack.size == 1 }
    }
    val context = LocalContext.current
    val title by remember {
        derivedStateOf {
            when (val top = topScreen) {
                is WeatherScreen -> top.selectedCity.name
                else -> context.getString(com.francescsoftware.weathersample.ui.shared.assets.R.string.app_name)
            }
        }
    }
    LaunchedEffect(key1 = isConnected) {
        if (!isConnected) {
            snackbarHostState.showSnackbar(
                message = networkLostMessage,
                duration = SnackbarDuration.Indefinite,
            )
        }
    }
    WeatherSampleTheme {
        Scaffold(
            modifier = Modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets.statusBars,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                AppBar(
                    title = title,
                    navigationIcon = {
                        if (!isAtRoot) {
                            NavigationIcon(onClick = { navigator.pop() })
                        }
                    },
                    actions = {
                        if (isAtRoot) {

                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            },
            bottomBar = if (state.hasBottomNavBar) {
                {
                    AnimatedVisibility(
                        visible = isAtRoot,
                        enter = slideInVertically { it } + fadeIn(),
                        exit = slideOutVertically { it } + fadeOut(),
                    ) {
                        BottomNavBar(
                            selectedScreen = rootScreen,
                            onClick = { screen ->
                                navigator.goTo(screen)
                            },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            } else {
                {}
            },
        ) { paddingValues ->
            Box {
                Row(
                    modifier = Modifier
                        .padding(paddingValues)
                        .consumeWindowInsets(paddingValues),
                ) {
                    if (state.hasNavRail) {
                        NavRail(
                            selectedScreen = rootScreen,
                            onClick = { screen ->
                                navigator.goTo(screen)
                            },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                    NavigableCircuitContent(
                        navigator = navigator,
                        backstack = backstack,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    )
                }
                if (state.hasBottomNavBar && isAtRoot) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .windowInsetsBottomHeight(WindowInsets.navigationBars)
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.background,
                                    ),
                                ),
                            ),
                    )
                }
            }
        }
    }
}

@Composable
private fun NavigationIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(id = assetsR.string.content_description_back),
        )
    }
}
