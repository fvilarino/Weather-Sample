package com.francescsoftware.weathersample.ui.feature.search.city.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.francescsoftware.weathersample.ui.feature.search.R
import com.francescsoftware.weathersample.ui.feature.search.city.model.CityResultModel
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import com.francescsoftware.weathersample.ui.feature.search.city.viewmodel.CityState
import com.francescsoftware.weathersample.ui.feature.search.city.viewmodel.CityViewModel
import com.francescsoftware.weathersample.ui.feature.search.city.viewmodel.LoadState
import com.francescsoftware.weathersample.ui.feature.search.navigation.CitySearchDestination
import com.francescsoftware.weathersample.ui.feature.search.navigation.SelectedCity
import com.francescsoftware.weathersample.ui.shared.composable.common.DualPane
import com.francescsoftware.weathersample.ui.shared.composable.common.GenericMessage
import com.francescsoftware.weathersample.ui.shared.composable.common.ProgressIndicator
import com.francescsoftware.weathersample.ui.shared.deviceclass.DeviceClass
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginQuad
import com.francescsoftware.weathersample.ui.shared.styles.PhonePreviews
import com.francescsoftware.weathersample.ui.shared.styles.TabletPreviews
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal val MinColumnWidth = 300.dp

@Composable
internal fun CityScreen(
    viewModel: CityViewModel,
    deviceClass: DeviceClass,
    onCityClick: (SelectedCity) -> Unit,
    modifier: Modifier = Modifier,
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
        onFavoriteClick = viewModel::onFavoriteClick,
        onQueryChange = viewModel::onQueryChange,
        onQueryFocused = viewModel::onQueryFocused,
        onChipClick = viewModel::onChipClick,
        onDeleteChip = viewModel::onDeleteChip,
        modifier = modifier,
    )
}

@Composable
internal fun CityScreen(
    state: CityState,
    actions: CitySearchDestination.ActionsState,
    deviceClass: DeviceClass,
    onCityClick: (SelectedCity) -> Unit,
    onFavoriteClick: (CityResultModel) -> Unit,
    onQueryChange: (TextFieldValue) -> Unit,
    onQueryFocused: () -> Unit,
    onChipClick: (RecentCityModel) -> Unit,
    onDeleteChip: (RecentCityModel) -> Unit,
    modifier: Modifier = Modifier,
    stateHolder: CityScreenStateHolder = rememberCityScreenStateHolder(),
) {
    val keyboardController = LocalSoftwareKeyboardController.current

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
                onChipClick = { recentCityModel ->
                    keyboardController?.hide()
                    onChipClick(recentCityModel)
                },
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
                onFavoriteClick = onFavoriteClick,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = MarginDouble),
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
        val cityChipsContentDescription = stringResource(
            id = R.string.content_description_city_chips,
        )

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
                cities = state.recentCities,
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
                    .semantics { contentDescription = cityChipsContentDescription }
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
    onFavoriteClick: (CityResultModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val citiesLoading = stringResource(id = R.string.content_description_cities_loading)
    val citiesResults = stringResource(id = R.string.content_description_cities_result)
    val citiesNoResults = stringResource(id = R.string.content_description_cities_no_results)
    val citiesError = stringResource(id = R.string.content_description_cities_error)
    Crossfade(
        targetState = state.loadState,
        modifier = modifier,
        label = "cityCrossFade",
    ) { loadState ->
        when (loadState) {
            LoadState.Idle -> {
            }

            LoadState.Loading -> ProgressIndicator(
                modifier = Modifier
                    .semantics { contentDescription = citiesLoading }
                    .fillMaxSize(),
            )

            LoadState.Loaded -> CitiesList(
                state = state,
                onCityClick = onCityClick,
                onFavoriteClick = onFavoriteClick,
                modifier = Modifier
                    .semantics { contentDescription = citiesResults }
                    .fillMaxSize(),
            )

            LoadState.NoResults -> GenericMessage(
                message = stringResource(id = R.string.no_results_found_label),
                modifier = Modifier
                    .semantics { contentDescription = citiesNoResults }
                    .fillMaxSize(),
            )

            LoadState.Error -> GenericMessage(
                message = stringResource(id = R.string.city_error_loading),
                icon = Icons.Default.Warning,
                modifier = Modifier
                    .semantics { contentDescription = citiesError }
                    .fillMaxSize(),
            )
        }
    }
}

@PhonePreviews
@TabletPreviews
@Composable
private fun PreviewCityScreen() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            val state = remember {
                CityState(
                    loadState = LoadState.Loaded,
                    cities = persistentListOf(
                        VancouverCityModel,
                        BarcelonaCityModel,
                        LondonCityModel,
                    ),
                    recentCities = persistentListOf(
                        RecentCityModel("Vancouver"),
                        RecentCityModel("Barcelona"),
                        RecentCityModel("London"),
                        RecentCityModel("Tokyo"),
                        RecentCityModel("Jakarta"),
                    ),
                    showRecentCities = true,
                )
            }
            CityScreen(
                state = state,
                actions = CitySearchDestination.ActionsState(),
                deviceClass = DeviceClass.Compact,
                onCityClick = { },
                onFavoriteClick = { },
                onQueryChange = { },
                onQueryFocused = { },
                onChipClick = { },
                onDeleteChip = { },
            )
        }
    }
}
