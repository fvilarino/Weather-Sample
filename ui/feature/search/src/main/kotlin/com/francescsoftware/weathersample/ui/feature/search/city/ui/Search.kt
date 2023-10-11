package com.francescsoftware.weathersample.ui.feature.search.city.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.francescsoftware.weathersample.ui.feature.search.R
import com.francescsoftware.weathersample.ui.feature.search.city.model.CityResultModel
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import com.francescsoftware.weathersample.ui.feature.search.city.model.SelectedCity
import com.francescsoftware.weathersample.ui.feature.search.city.presenter.SearchScreen
import com.francescsoftware.weathersample.ui.shared.composable.common.composition.LocalWindowSizeClass
import com.francescsoftware.weathersample.ui.shared.composable.common.composition.isCompact
import com.francescsoftware.weathersample.ui.shared.composable.common.composition.isExpanded
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.Crossfade
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.DualPane
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.GenericMessage
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.PanesOrientation
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.ProgressIndicator
import com.francescsoftware.weathersample.ui.shared.styles.LandscapePhonePreviews
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginQuad
import com.francescsoftware.weathersample.ui.shared.styles.PhoneDpSize
import com.francescsoftware.weathersample.ui.shared.styles.PhonePreviews
import com.francescsoftware.weathersample.ui.shared.styles.TabletPreviews
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.slack.circuit.codegen.annotations.CircuitInject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal val MinColumnWidth = 300.dp
private const val CityPaneWeight = 3f

@CircuitInject(SearchScreen::class, ActivityScope::class)
@Composable
internal fun Search(
    state: SearchScreen.State,
    modifier: Modifier = Modifier,
) {
    CityScreen(
        state = state,
        onCityClick = { selectedCity -> state.eventSink(SearchScreen.Event.CityClick(selectedCity)) },
        onFavoriteClick = { state.eventSink(SearchScreen.Event.FavoriteClick(it)) },
        onQueryChange = { state.eventSink(SearchScreen.Event.QueryUpdated(it)) },
        onChipClick = { state.eventSink(SearchScreen.Event.ChipClick(it)) },
        onDeleteChip = { state.eventSink(SearchScreen.Event.DeleteChipClick(it)) },
        modifier = modifier,
    )
}

@Composable
@OptIn(ExperimentalComposeUiApi::class)
internal fun CityScreen(
    state: SearchScreen.State,
    onCityClick: (SelectedCity) -> Unit,
    onFavoriteClick: (CityResultModel) -> Unit,
    onQueryChange: (TextFieldValue) -> Unit,
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
        Column(modifier = modifier) {
            CityPane(
                state = state,
                stateHolder = stateHolder,
                onChipClick = { recentCityModel ->
                    keyboardController?.hide()
                    onChipClick(recentCityModel)
                },
                onDeleteChip = onDeleteChip,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MarginDouble),
            )
            Cities(
                state = state,
                onCityClick = onCityClick,
                onFavoriteClick = onFavoriteClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
        }
    } else {
        DualPane(
            panesOrientation = PanesOrientation.horizontal(
                aspectRatio = if (windowSizeClass.isExpanded) .33f else .5f,
            ),
            paneOne = {
                Column(modifier = Modifier.fillMaxSize()) {
                    Spacer(
                        modifier = if (windowSizeClass.isCompact) {
                            Modifier.height(MarginQuad)
                        } else {
                            Modifier.weight(1f)
                        },
                    )
                    CityPane(
                        state = state,
                        stateHolder = stateHolder,
                        onChipClick = { recentCityModel ->
                            keyboardController?.hide()
                            onChipClick(recentCityModel)
                        },
                        onDeleteChip = onDeleteChip,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(CityPaneWeight)
                            .padding(horizontal = MarginDouble),
                    )
                }
            },
            paneTwo = {
                Cities(
                    state = state,
                    onCityClick = onCityClick,
                    onFavoriteClick = onFavoriteClick,
                    modifier = Modifier.fillMaxSize(),
                )
            },
            modifier = modifier,
        )
    }
}

@Composable
private fun CityPane(
    state: SearchScreen.State,
    stateHolder: CityScreenStateHolder,
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
            modifier = Modifier
                .width(MinColumnWidth)
                .padding(top = MarginQuad),
        )
        AnimatedVisibility(
            visible = state.recentCities.isNotEmpty(),
        ) {
            CityChips(
                cities = state.recentCities,
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
    state: SearchScreen.State,
    onCityClick: (SelectedCity) -> Unit,
    onFavoriteClick: (CityResultModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val citiesLoading = stringResource(id = R.string.test_tag_cities_loading)
    val citiesResults = stringResource(id = R.string.test_tag_cities_result)
    val citiesNoResults = stringResource(id = R.string.test_tag_cities_no_results)
    val citiesError = stringResource(id = R.string.test_tag_cities_error)
    Crossfade(
        targetState = state.citiesResult,
        contentKey = { it::class.java },
        modifier = modifier,
    ) { result ->
        when (result) {
            SearchScreen.CitiesResult.Idle -> {}
            SearchScreen.CitiesResult.Loading -> ProgressIndicator(
                modifier = Modifier
                    .semantics { testTag = citiesLoading }
                    .fillMaxSize(),
            )

            SearchScreen.CitiesResult.NoResults -> GenericMessage(
                message = stringResource(id = R.string.no_results_found_label),
                modifier = Modifier
                    .semantics { testTag = citiesNoResults }
                    .fillMaxSize(),
            )

            SearchScreen.CitiesResult.Error -> GenericMessage(
                message = stringResource(id = R.string.city_error_loading),
                icon = Icons.Default.Warning,
                modifier = Modifier
                    .semantics { testTag = citiesError }
                    .fillMaxSize(),
            )

            is SearchScreen.CitiesResult.Loaded -> CitiesList(
                cities = result.cities,
                onCityClick = onCityClick,
                onFavoriteClick = onFavoriteClick,
                modifier = Modifier
                    .semantics { testTag = citiesResults }
                    .fillMaxSize()
                    .padding(horizontal = MarginDouble),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@PhonePreviews
@LandscapePhonePreviews
@TabletPreviews
@Composable
private fun PreviewCityScreen() {
    WeatherSampleTheme {
        CompositionLocalProvider(
            LocalWindowSizeClass provides WindowSizeClass.calculateFromSize(PhoneDpSize),
        ) {
            Surface(
                color = MaterialTheme.colorScheme.background,
            ) {
                val state = remember {
                    SearchScreen.State(
                        citiesResult = SearchScreen.CitiesResult.Loaded(
                            cities = persistentListOf(
                                VancouverCityModel,
                                BarcelonaCityModel,
                                LondonCityModel,
                            ),
                        ),
                        recentCities = persistentListOf(
                            RecentCityModel("Vancouver"),
                            RecentCityModel("Barcelona"),
                            RecentCityModel("London"),
                            RecentCityModel("Tokyo"),
                            RecentCityModel("Jakarta"),
                        ),
                        eventSink = {},
                    )
                }
                CityScreen(
                    state = state,
                    onCityClick = { },
                    onFavoriteClick = { },
                    onQueryChange = { },
                    onChipClick = { },
                    onDeleteChip = { },
                )
            }
        }
    }
}
