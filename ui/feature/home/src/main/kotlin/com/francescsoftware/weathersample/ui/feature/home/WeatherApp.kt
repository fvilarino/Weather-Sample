package com.francescsoftware.weathersample.ui.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import com.francescsoftware.weathersample.ui.feature.favorites.navigation.FavoritesRootDestination
import com.francescsoftware.weathersample.ui.feature.favorites.navigation.addFavoritesNavGraph
import com.francescsoftware.weathersample.ui.feature.search.navigation.SearchRootDestination
import com.francescsoftware.weathersample.ui.feature.search.navigation.addSearchNavGraph
import com.francescsoftware.weathersample.ui.shared.assets.R
import com.francescsoftware.weathersample.ui.shared.composable.common.AppBar
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import kotlinx.collections.immutable.persistentListOf

internal val navGraphDestinations = persistentListOf(
    SearchRootDestination,
    FavoritesRootDestination,
)

@Composable
internal fun WeatherApp(
    state: AppState = rememberAppState()
) {
    WeatherSampleTheme {
        Scaffold(
            contentWindowInsets = WindowInsets.statusBars,
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
                    modifier = Modifier.padding(paddingValues),
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
                                    )
                                )
                            )
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
            contentDescription = stringResource(id = R.string.content_description_back),
        )
    }
}
