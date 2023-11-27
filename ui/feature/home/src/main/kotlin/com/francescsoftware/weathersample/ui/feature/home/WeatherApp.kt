package com.francescsoftware.weathersample.ui.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.francescsoftware.weathersample.core.connectivity.api.ConnectivityMonitor
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.GetPreferencesInteractor
import com.francescsoftware.weathersample.ui.feature.favorites.navigation.FavoritesDestination
import com.francescsoftware.weathersample.ui.feature.search.city.presenter.SearchScreen
import com.francescsoftware.weathersample.ui.feature.search.navigation.SearchDestination
import com.francescsoftware.weathersample.ui.feature.settings.navigation.SettingsDestination
import com.francescsoftware.weathersample.ui.shared.composable.common.modifier.blurIf
import com.francescsoftware.weathersample.ui.shared.composable.common.overlay.DialogOverlay
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.ActionMenuItem
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.ActionsMenu
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.AppBar
import com.francescsoftware.weathersample.ui.shared.deeplink.DeeplinkParser
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.overlay.LocalOverlayHost
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNot
import com.francescsoftware.weathersample.ui.shared.assets.R as assetsR

internal val navigationDestinations = persistentListOf(
    SearchDestination,
    FavoritesDestination,
    SettingsDestination,
)

private const val BackgroundGradientStart = .33f

@Composable
internal fun WeatherApp(
    connectivityMonitor: ConnectivityMonitor,
    deeplinkParser: DeeplinkParser,
    preferencesInteractor: GetPreferencesInteractor,
    state: AppState = rememberAppState(
        connectivityMonitor = connectivityMonitor,
        preferencesInteractor = preferencesInteractor,
    ),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val isConnected by state.isConnected.collectAsStateWithLifecycle()
    val theme by state.appTheme.collectAsStateWithLifecycle()
    val dynamicColors by state.useDynamicColors.collectAsStateWithLifecycle()
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
    val navDestination by remember {
        derivedStateOf {
            navigationDestinations.first {
                it.rootScreen == rootScreen
            }
        }
    }
    val title = navDestination.actionBarLabel(screen = topScreen)
    var menuExpanded by remember { mutableStateOf(false) }
    var showAbout by rememberSaveable { mutableStateOf(false) }
    val overlayHost = LocalOverlayHost.current
    val blurBackground by remember {
        derivedStateOf {
            overlayHost.currentOverlayData != null
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
    LaunchedEffect(key1 = deeplinkParser) {
        deeplinkParser.events
            .filterNot { payload -> payload.isConsumed }
            .collectLatest { payload ->
                payload.consume()?.forEachIndexed { index, screen ->
                    if (index == 0) {
                        navigator.resetRoot(screen)
                    } else {
                        navigator.goTo(screen)
                    }
                }
            }
    }
    WeatherSampleTheme(
        darkTheme = useDarkTheme(theme),
        useDynamicColors = useDynamicColors(dynamicColors),
    ) {
        Scaffold(
            modifier = Modifier
                .blurIf(blur = blurBackground)
                .nestedScroll(connection = scrollBehavior.nestedScrollConnection),
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
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
                            ActionsMenu(
                                items = persistentListOf(
                                    ActionMenuItem.NeverShown(
                                        title = stringResource(
                                            id = assetsR.string.action_item_about,
                                        ),
                                        onClick = {
                                            menuExpanded = false
                                            showAbout = true
                                        },
                                    ),
                                ),
                                isOpen = menuExpanded,
                                onToggleOverflow = { menuExpanded = !menuExpanded },
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            },
            bottomBar = {
                if (state.hasBottomNavBar) {
                    BottomNavBar(
                        selectedScreen = rootScreen,
                        onClick = { screen ->
                            navigator.resetRoot(screen)
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            },
        ) { paddingValues ->
            AppBackground(
                modifier = Modifier.fillMaxSize(),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .consumeWindowInsets(paddingValues)
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal,
                            ),
                        ),
                ) {
                    if (state.hasNavRail) {
                        NavRail(
                            selectedScreen = rootScreen,
                            onClick = { screen ->
                                navigator.resetRoot(screen)
                            },
                            modifier = Modifier.align(Alignment.CenterVertically),
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
                if (showAbout) {
                    LaunchedEffect(key1 = Unit) {
                        overlayHost.show(
                            aboutOverlay(),
                        )
                        showAbout = false
                    }
                }
            }
        }
    }
}

@Composable
private fun AppBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    BackgroundGradientStart to MaterialTheme.colorScheme.surface,
                    1f to MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                ),
            ),
    ) {
        content()
    }
}

@Composable
private fun useDarkTheme(theme: SystemTheme) = when (theme) {
    SystemTheme.Undefined,
    SystemTheme.FollowSystem,
    -> isSystemInDarkTheme()

    SystemTheme.Light -> false
    SystemTheme.Dark -> true
}

private fun useDynamicColors(dynamicColors: DynamicColors) = when (dynamicColors) {
    DynamicColors.Undefined,
    DynamicColors.InUse,
    -> true

    DynamicColors.NotInUse -> false
}

private fun aboutOverlay() = DialogOverlay(
    confirmButtonText = {
        Text(
            text = stringResource(id = android.R.string.ok),
        )
    },
    title = {
        Text(
            text = stringResource(id = com.francescsoftware.weathersample.ui.shared.assets.R.string.app_name),
        )
    },
    text = {
        Text(
            text = stringResource(
                id = com.francescsoftware.weathersample.ui.shared.assets.R.string.about_dialog_text,
            ),
        )
    },
)

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
