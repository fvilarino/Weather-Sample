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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.francescsoftware.weathersample.feature.city.model.RecentCityModel
import com.francescsoftware.weathersample.feature.city.viewmodel.CityState
import com.francescsoftware.weathersample.feature.city.viewmodel.CityViewModel
import com.francescsoftware.weathersample.feature.city.viewmodel.LoadState
import com.francescsoftware.weathersample.presentation.route.SelectedCity
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.MarginQuad
import com.francescsoftware.weathersample.styles.PhonePreviews
import com.francescsoftware.weathersample.styles.TabletPreviews
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal val MinColumnWidth = 360.dp

@Composable
internal fun CityScreen(
    viewModel: CityViewModel,
    onCityClick: (SelectedCity) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CityScreen(
        state = state,
        onCityClick = { selectedCity ->
            viewModel.onCityClick(selectedCity)
            onCityClick(selectedCity)
        },
        onQueryChange = viewModel::onQueryChange,
        onQueryFocused = viewModel::onQueryFocused,
        onChipClick = viewModel::onChipClick,
        onDeleteChip = viewModel::onDeleteChip,
        modifier = modifier,
    )
}

@Composable
private fun CityScreen(
    state: CityState,
    onCityClick: (SelectedCity) -> Unit,
    onQueryChange: (TextFieldValue) -> Unit,
    onQueryFocused: () -> Unit,
    onChipClick: (RecentCityModel) -> Unit,
    onDeleteChip: (RecentCityModel) -> Unit,
    modifier: Modifier = Modifier,
    stateHolder: CityScreenStateHolder = rememberCityScreenStateHolder(),
) {
    LaunchedEffect(key1 = Unit) {
        snapshotFlow { stateHolder.query }
            .onEach(onQueryChange)
            .launchIn(this)
    }

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
                onChipClick = onChipClick,
                onDeleteChip = onDeleteChip,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MarginDouble, start = MarginDouble, end = MarginDouble)
            )
        }
        Crossfade(
            targetState = state.loadState,
            modifier = modifier.padding(top = MarginDouble),
        ) { loadState ->
            when (loadState) {
                LoadState.Idle -> {
                }

                LoadState.Loading -> CitiesLoading(
                    modifier = Modifier.fillMaxSize(),
                )

                LoadState.Loaded -> CitiesList(
                    state = state,
                    onCityClick = onCityClick,
                    modifier = Modifier.fillMaxSize(),
                )

                LoadState.NoResults -> CitiesNoResults(
                    modifier = Modifier.fillMaxSize(),
                )

                LoadState.Error -> CitiesLoadError(
                    modifier = Modifier.fillMaxSize(),
                )
            }
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
