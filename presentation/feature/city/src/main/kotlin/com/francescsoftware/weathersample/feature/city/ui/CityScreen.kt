package com.francescsoftware.weathersample.feature.city.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.francescsoftware.weathersample.deviceclass.DeviceClass
import com.francescsoftware.weathersample.feature.city.model.RecentCityModel
import com.francescsoftware.weathersample.feature.city.viewmodel.CityState
import com.francescsoftware.weathersample.feature.city.viewmodel.CityViewModel
import com.francescsoftware.weathersample.feature.city.viewmodel.LoadState
import com.francescsoftware.weathersample.presentation.route.CitySearchDestination
import com.francescsoftware.weathersample.presentation.route.SelectedCity
import com.francescsoftware.weathersample.shared.composable.DualPane
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.MarginQuad
import com.francescsoftware.weathersample.styles.PhonePreviews
import com.francescsoftware.weathersample.styles.TabletPreviews
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal val MinColumnWidth = 300.dp

@Composable
internal fun CityScreen(
    viewModel: CityViewModel,
    deviceClass: DeviceClass,
    onCityClick: (SelectedCity) -> Unit,
    modifier: Modifier = Modifier,
    navPadding: Dp = 0.dp,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val actions by CitySearchDestination.actionsState.collectAsStateWithLifecycle()

    CityScreen(
        state = state,
        actions = actions,
        deviceClass = deviceClass,
        onCityClick = { selectedCity ->
            viewModel.onCityClick(selectedCity)
            onCityClick(selectedCity)
        },
        onQueryChange = viewModel::onQueryChange,
        onQueryFocused = viewModel::onQueryFocused,
        onChipClick = viewModel::onChipClick,
        onDeleteChip = viewModel::onDeleteChip,
        modifier = modifier,
        navPadding = navPadding,
    )
}

@Composable
private fun CityScreen(
    state: CityState,
    actions: CitySearchDestination.ActionsState,
    deviceClass: DeviceClass,
    onCityClick: (SelectedCity) -> Unit,
    onQueryChange: (TextFieldValue) -> Unit,
    onQueryFocused: () -> Unit,
    onChipClick: (RecentCityModel) -> Unit,
    onDeleteChip: (RecentCityModel) -> Unit,
    modifier: Modifier = Modifier,
    navPadding: Dp = 0.dp,
    stateHolder: CityScreenStateHolder = rememberCityScreenStateHolder(),
) {
    LaunchedEffect(key1 = Unit) {
        snapshotFlow { stateHolder.query }
            .onEach(onQueryChange)
            .launchIn(this)
    }

    DualPane(
        paneOne = {
            CityPane(
                state = state,
                stateHolder = stateHolder,
                onQueryFocused = onQueryFocused,
                onChipClick = onChipClick,
                onDeleteChip = onDeleteChip,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MarginDouble),
            )
        },
        paneTwo = {
            Cities(
                state = state,
                onCityClick = onCityClick,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = MarginDouble),
                navPadding = navPadding,
            )
        },
        deviceClass = deviceClass,
        modifier = modifier,
    )
    if (actions.about) {
        AboutDialog(
            onDismiss = {
                CitySearchDestination.consume(CitySearchDestination.Actions.About)
            }
        )
    }
}

@Composable
private fun CityPane(
    state: CityState,
    stateHolder: CityScreenStateHolder,
    onQueryFocused: () -> Unit,
    onChipClick: (RecentCityModel) -> Unit,
    onDeleteChip: (RecentCityModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CitiesSearchBox(
            query = stateHolder.query,
            onQueryChange = stateHolder::onQueryUpdated,
            onClearQuery = stateHolder::onClearQuery,
            onQueryFocused = onQueryFocused,
            modifier = Modifier
                .width(MinColumnWidth)
                .padding(top = MarginQuad)
        )
        AnimatedVisibility(
            visible = state.showRecentCities,
        ) {
            CityChips(
                state = state,
                onChipClick = { cityModel ->
                    stateHolder.onQueryUpdated(
                        TextFieldValue(
                            cityModel.name,
                            TextRange(cityModel.name.length),
                        )
                    )
                    onChipClick(cityModel)
                },
                onDeleteChip = onDeleteChip,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MarginDouble)
            )
        }
    }
}

@Composable
private fun Cities(
    state: CityState,
    onCityClick: (SelectedCity) -> Unit,
    modifier: Modifier = Modifier,
    navPadding: Dp = 0.dp,
) {
    Crossfade(
        targetState = state.loadState,
        modifier = modifier,
    ) { loadState ->
        when (loadState) {
            LoadState.Idle -> {
            }

            LoadState.Loading -> CitiesLoading(
                modifier = Modifier.fillMaxSize().padding(bottom = navPadding),
            )

            LoadState.Loaded -> CitiesList(
                state = state,
                onCityClick = onCityClick,
                modifier = Modifier.fillMaxSize(),
                navPadding = navPadding,
            )

            LoadState.NoResults -> CitiesNoResults(
                modifier = Modifier.fillMaxSize().padding(bottom = navPadding),
            )

            LoadState.Error -> CitiesLoadError(
                modifier = Modifier.fillMaxSize().padding(bottom = navPadding),
            )
        }
    }
}

@PhonePreviews
@TabletPreviews
@Composable
private fun CityScreenPreview() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            var state by remember {
                mutableStateOf(
                    CityState(
                        loadState = LoadState.Loaded,
                        query = TextFieldValue(),
                        cities = listOf(
                            VancouverCityModel,
                            BarcelonaCityModel,
                            LondonCityModel,
                        ),
                        recentCities = listOf(
                            RecentCityModel("Vancouver"),
                            RecentCityModel("Barcelona"),
                            RecentCityModel("London"),
                            RecentCityModel("Tokyo"),
                            RecentCityModel("Jakarta"),
                        ),
                        showRecentCities = true,
                    )
                )
            }
            CityScreen(
                state = state,
                actions = CitySearchDestination.ActionsState(),
                deviceClass = DeviceClass.Compact,
                onQueryChange = { query ->
                    state = state.copy(query = query)
                },
                onQueryFocused = {},
                onCityClick = {},
                onChipClick = {},
                onDeleteChip = {},
            )
        }
    }
}
