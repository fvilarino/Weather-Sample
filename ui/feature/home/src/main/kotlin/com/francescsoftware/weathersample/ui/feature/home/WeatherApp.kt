package com.francescsoftware.weathersample.ui.feature.home

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.francescsoftware.weathersample.core.connectivity.api.ConnectivityMonitor
import com.francescsoftware.weathersample.ui.feature.favorites.navigation.FavoritesRootDestination
import com.francescsoftware.weathersample.ui.feature.search.navigation.SearchRootDestination
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.AppBar
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import kotlinx.collections.immutable.persistentListOf
import com.francescsoftware.weathersample.ui.shared.assets.R as assetsR

internal val navGraphDestinations = persistentListOf(
    SearchRootDestination,
    FavoritesRootDestination,
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
                    title = state.currentDestination.title,
                    navigationIcon = if (state.hasBackButton) {
                        {
                            NavigationIcon(onClick = state::popBackstack)
                        }
                    } else {
                        {}
                    },
                    actions = { state.currentDestination.TopBarActions() },
                    scrollBehavior = scrollBehavior,
                )
            },
            bottomBar = if (state.hasBottomNavBar) {
                {
                    AnimatedVisibility(
                        visible = state.showBottomNavBar,
                        enter = slideInVertically { it } + fadeIn(),
                        exit = slideOutVertically { it } + fadeOut(),
                    ) {
                        BottomNavBar(
                            items = navGraphDestinations,
                            currentDestination = state.navBackEntryDestination,
                            onClick = { destination ->
                                state.navigateToTopDestination(destination)
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
                            items = navGraphDestinations,
                            currentDestination = state.navBackEntryDestination,
                            onClick = { destination ->
                                state.navigateToTopDestination(destination)
                            },
                        )
                    }
/*
                    NavHost(
                        state.navHostController,
                        startDestination = navGraphDestinations.first().navGraphRoute,
                        modifier = Modifier.weight(1f),
                    ) {
                        addSearchNavGraph(
                            deviceClass = state.deviceClass,
                        ) { route ->
                            state.navigate(route)
                        }
                        addFavoritesNavGraph(
                            deviceClass = state.deviceClass,
                        )
                    }
*/
                }
                if (state.showBottomOverlay) {
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
