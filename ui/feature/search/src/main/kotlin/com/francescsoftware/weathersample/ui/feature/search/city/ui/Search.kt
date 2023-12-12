package com.francescsoftware.weathersample.ui.feature.search.city.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City
import com.francescsoftware.weathersample.ui.feature.search.R
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import com.francescsoftware.weathersample.ui.feature.search.city.presenter.SearchScreen
import com.francescsoftware.weathersample.ui.shared.composable.common.composition.LocalWindowSizeClass
import com.francescsoftware.weathersample.ui.shared.composable.common.composition.isExpanded
import com.francescsoftware.weathersample.ui.shared.composable.common.saver.intSizeSaver
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.DualPane
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.GenericMessage
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.PanesOrientation
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.ProgressIndicator
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginQuad
import com.francescsoftware.weathersample.ui.shared.styles.MarginSingle
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal val MinColumnWidth = 304.dp
private val LoadingMoreIndicatorSize = 48.dp

@CircuitInject(SearchScreen::class, ActivityScope::class)
@Composable
internal fun Search(
    state: SearchScreen.State,
    modifier: Modifier = Modifier,
) {
    val eventSink = state.eventSink
    CityScreen(
        cities = state.cities,
        favoriteCities = state.favoriteCities,
        recentCities = state.recentCities,
        showResults = state.showResults,
        onLocationClick = { eventSink(SearchScreen.Event.LocationClick) },
        onCityClick = { selectedCity -> eventSink(SearchScreen.Event.CityClick(selectedCity)) },
        onFavoriteClick = { eventSink(SearchScreen.Event.FavoriteClick(it)) },
        onQueryChange = { eventSink(SearchScreen.Event.QueryUpdated(it)) },
        onRetryClick = { eventSink(SearchScreen.Event.RetryClick) },
        onChipClick = { eventSink(SearchScreen.Event.ChipClick(it)) },
        onDeleteChip = { eventSink(SearchScreen.Event.DeleteChipClick(it)) },
        modifier = modifier,
    )
}

@Composable
internal fun CityScreen(
    cities: LazyPagingItems<City>,
    favoriteCities: ImmutableSet<Long>,
    recentCities: ImmutableList<RecentCityModel>,
    showResults: Boolean,
    onLocationClick: () -> Unit,
    onCityClick: (City) -> Unit,
    onFavoriteClick: (City) -> Unit,
    onQueryChange: (TextFieldValue) -> Unit,
    onRetryClick: () -> Unit,
    onChipClick: (RecentCityModel) -> Unit,
    onDeleteChip: (RecentCityModel) -> Unit,
    modifier: Modifier = Modifier,
    stateHolder: CityScreenStateHolder = rememberCityScreenStateHolder(),
) {
    val windowSizeClass = LocalWindowSizeClass.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val orientation = LocalConfiguration.current.orientation

    LaunchedEffect(key1 = Unit) {
        snapshotFlow { stateHolder.query }
            .onEach(onQueryChange)
            .launchIn(this)
    }

    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        var headerSize: IntSize by rememberSaveable(
            stateSaver = intSizeSaver(),
        ) {
            mutableStateOf(IntSize.Zero)
        }
        val headerHeightDp = with(LocalDensity.current) { headerSize.height.toDp() }
        val hazeState = remember { HazeState() }
        Box(
            modifier = modifier
        ) {
            Cities(
                cities = cities,
                favoriteCities = favoriteCities,
                showResults = showResults,
                onCityClick = onCityClick,
                onFavoriteClick = onFavoriteClick,
                onRetryClick = onRetryClick,
                modifier = Modifier
                    .fillMaxSize()
                    .haze(
                        state = hazeState,
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        tint = MaterialTheme.colorScheme.surface.copy(alpha = .5f),
                        blurRadius = 16.dp,
                    ),
                contentPadding = PaddingValues(
                    start = MarginDouble,
                    top = MarginDouble + headerHeightDp,
                    end = MarginDouble,
                    bottom = MarginDouble,
                ),
            )
            CityPane(
                recentCities = recentCities,
                stateHolder = stateHolder,
                singleLine = true,
                onLocationClick = onLocationClick,
                onChipClick = { recentCityModel ->
                    keyboardController?.hide()
                    onChipClick(recentCityModel)
                },
                onDeleteChip = onDeleteChip,
                modifier = Modifier
                    .hazeChild(state = hazeState)
                    .onPlaced {
                        headerSize = it.size
                    }
                    .fillMaxWidth(),
            )
        }
    } else {
        DualPane(
            panesOrientation = PanesOrientation.horizontal(
                aspectRatio = if (windowSizeClass.isExpanded) .4f else .5f,
            ),
            paneOne = {
                Column(modifier = Modifier.fillMaxSize()) {
                    Spacer(
                        modifier = Modifier.height(MarginQuad),
                    )
                    CityPane(
                        recentCities = recentCities,
                        stateHolder = stateHolder,
                        singleLine = false,
                        onLocationClick = onLocationClick,
                        onChipClick = { recentCityModel ->
                            keyboardController?.hide()
                            onChipClick(recentCityModel)
                        },
                        onDeleteChip = onDeleteChip,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = MarginDouble),
                    )
                }
            },
            paneTwo = {
                Cities(
                    cities = cities,
                    favoriteCities = favoriteCities,
                    showResults = showResults,
                    onCityClick = onCityClick,
                    onFavoriteClick = onFavoriteClick,
                    onRetryClick = onRetryClick,
                    modifier = Modifier.fillMaxSize(),
                )
            },
            modifier = modifier,
        )
    }
}

@Composable
private fun CityPane(
    recentCities: ImmutableList<RecentCityModel>,
    stateHolder: CityScreenStateHolder,
    singleLine: Boolean,
    onLocationClick: () -> Unit,
    onChipClick: (RecentCityModel) -> Unit,
    onDeleteChip: (RecentCityModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val cityChipsContentDescription = stringResource(
            id = R.string.test_tag_city_chips,
        )
        CitiesSearchBox(
            query = stateHolder.query,
            onQueryChange = stateHolder::onQueryUpdated,
            onClearQuery = stateHolder::onClearQuery,
            onLocationClick = onLocationClick,
            modifier = Modifier
                .widthIn(min = MinColumnWidth)
                .padding(top = MarginQuad),
        )
        AnimatedVisibility(
            visible = recentCities.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            CityChips(
                cities = recentCities,
                singleLine = singleLine,
                onChipClick = { cityModel ->
                    stateHolder.onQueryUpdated(
                        TextFieldValue(
                            cityModel.name,
                            TextRange(cityModel.name.length),
                        ),
                    )
                    onChipClick(cityModel)
                },
                onDeleteChip = onDeleteChip,
                modifier = Modifier
                    .semantics { testTag = cityChipsContentDescription }
                    .fillMaxWidth()
                    .padding(top = MarginDouble),
            )
        }
    }
}

@Composable
private fun Cities(
    cities: LazyPagingItems<City>,
    favoriteCities: ImmutableSet<Long>,
    showResults: Boolean,
    onCityClick: (City) -> Unit,
    onFavoriteClick: (City) -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(all = 0.dp),
) {
    val citiesLoading = stringResource(id = R.string.test_tag_cities_loading)
    val citiesResults = stringResource(id = R.string.test_tag_cities_result)
    val citiesNoResults = stringResource(id = R.string.test_tag_cities_no_results)
    val citiesError = stringResource(id = R.string.test_tag_cities_error)
    val refreshing by remember {
        derivedStateOf { cities.loadState.refresh is LoadState.Loading }
    }
    val loadError by remember {
        derivedStateOf { cities.loadState.refresh is LoadState.Error }
    }
    val loadingMore by remember {
        derivedStateOf { cities.loadState.append is LoadState.Loading }
    }
    val appendError by remember {
        derivedStateOf { cities.loadState.append is LoadState.Error }
    }
    val noResults by remember {
        derivedStateOf {
            cities.loadState.refresh is LoadState.NotLoading && cities.itemCount == 0
        }
    }

    Box(
        modifier = modifier,
    ) {
        if (showResults) {
            if (refreshing) {
                ProgressIndicator(
                    modifier = Modifier
                        .padding(contentPadding)
                        .semantics { testTag = citiesLoading }
                        .fillMaxSize(),
                )
            } else {
                val listState = rememberLazyGridState()
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(MinColumnWidth),
                    state = listState,
                    contentPadding = contentPadding,
                    horizontalArrangement = Arrangement.spacedBy(MarginDouble),
                    verticalArrangement = Arrangement.spacedBy(MarginDouble),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    when {
                        loadError -> item(
                            key = "load_error",
                            span = { GridItemSpan(maxLineSpan) },
                        ) {
                            GenericMessage(
                                message = stringResource(id = R.string.city_error_loading),
                                icon = Icons.Default.Warning,
                                modifier = Modifier
                                    .semantics { testTag = citiesError }
                                    .fillMaxWidth()
                                    .padding(vertical = MarginQuad),
                            )
                        }

                        noResults -> item(
                            key = "no_results",
                            span = { GridItemSpan(maxLineSpan) },
                        ) {
                            GenericMessage(
                                message = stringResource(id = R.string.no_results_found_label),
                                icon = Icons.Default.Warning,
                                modifier = Modifier
                                    .semantics { testTag = citiesNoResults }
                                    .fillMaxWidth()
                                    .padding(vertical = MarginQuad),
                            )
                        }

                        else -> {
                            items(
                                count = cities.itemCount,
                                key = cities.itemKey { it.id },
                            ) { index ->
                                val city = cities[index]
                                if (city != null) {
                                    CityCard(
                                        city = city,
                                        isFavorite = favoriteCities.contains(city.id),
                                        onClick = { selectedCity -> onCityClick(selectedCity) },
                                        onFavoriteClick = onFavoriteClick,
                                        modifier = Modifier
                                            .testTag("${citiesResults}_${city.id}")
                                            .fillMaxWidth(),
                                        contentPadding = PaddingValues(all = MarginSingle),
                                    )
                                }
                            }
                            if (loadingMore) {
                                item(
                                    key = "loading_more",
                                    span = { GridItemSpan(maxLineSpan) },
                                ) {
                                    LoadingMore(
                                        modifier = Modifier.padding(vertical = MarginDouble),
                                    )
                                }
                            }
                            if (appendError) {
                                item(
                                    key = "append_error",
                                    span = { GridItemSpan(maxLineSpan) },
                                ) {
                                    AppendError(
                                        onClick = onRetryClick,
                                        modifier = Modifier.padding(vertical = MarginDouble),
                                    )
                                }
                            }
                        }
                    }
                    item(key = "footer") {
                        Spacer(modifier = Modifier.navigationBarsPadding())
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingMore(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MarginSingle),
        ) {
            ProgressIndicator(
                modifier = Modifier.size(LoadingMoreIndicatorSize),
            )
            Text(
                text = stringResource(id = R.string.loading_more),
            )
        }
    }
}

@Composable
private fun AppendError(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Button(
            onClick = onClick,
        ) {
            Text(
                text = stringResource(id = R.string.retry),
            )
        }
    }
}
