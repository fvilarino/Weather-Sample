package com.francescsoftware.weathersample.feature.city

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.francescsoftware.weathersample.feature.city.viewmodel.CityViewModel
import com.francescsoftware.weathersample.presentation.route.SelectedCity
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.MarginQuad
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.MarginTreble
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

private val MinColumnWidth = 360.dp

@Composable
internal fun CityScreen(
    viewModel: CityViewModel,
    onCityClick: (SelectedCity) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CityScreen(
        state = state,
        onCityClick = onCityClick,
        onQueryChange = viewModel::onQueryChange,
        onClearQuery = viewModel::onClearQuery,
        modifier = modifier,
    )
}

@Composable
private fun CityScreen(
    state: CityState,
    onCityClick: (SelectedCity) -> Unit,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CitiesSearchBox(
            state = state,
            onQueryChange = onQueryChange,
            onClearQuery = onClearQuery,
        )
        Crossfade(
            targetState = state.loadState,
            modifier = modifier.padding(top = MarginTreble),
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

@Composable
private fun CitiesSearchBox(
    state: CityState,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
) {
    OutlinedTextField(
        value = state.query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .width(MinColumnWidth)
            .padding(top = MarginQuad),
        label = { Text(text = stringResource(id = R.string.search_city_hint)) },
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_location_city_24),
                contentDescription = null,
            )
        },
        trailingIcon = {
            IconButton(onClick = onClearQuery) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_clear_24),
                    contentDescription = stringResource(id = R.string.content_description_clear),
                )
            }
        }
    )
}

@Composable
private fun CitiesLoading(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun CitiesList(
    state: CityState,
    onCityClick: (SelectedCity) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(
            minSize = MinColumnWidth,
        ),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MarginDouble),
        verticalArrangement = Arrangement.spacedBy(MarginDouble),
        contentPadding = PaddingValues(all = MarginDouble),
    ) {
        items(state.cities) { city ->
            CityCard(
                city = city,
                onClick = { model -> onCityClick(model.toSelectedCity()) },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(all = MarginSingle),
            )
        }
    }
}

@Composable
private fun CitiesNoResults(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(id = R.string.no_results_found_label),
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
private fun CitiesLoadError(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.city_error_loading),
            style = MaterialTheme.typography.body1,
        )
    }
}

private fun CityResultModel.toSelectedCity() = SelectedCity(
    name = name.toString(),
    country = country.toString(),
    countryCode = countryCode,
)

@Preview(group = "city", widthDp = 420, heightDp = 720)
@Preview(
    group = "city",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 420,
    heightDp = 720
)
@Preview(group = "city", device = Devices.PIXEL_C)
@Composable
private fun CityScreenPreview() {
    WeatherSampleTheme {
        var state by remember {
            mutableStateOf(
                CityState(
                    loadState = LoadState.Loaded,
                    query = "",
                    cities = listOf(
                        Vancouver,
                        Barcelona,
                        London,
                    ),
                )
            )
        }
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            CityScreen(
                state = state,
                onQueryChange = { query ->
                    state = state.copy(query = query)
                },
                onClearQuery = {
                    state = state.copy(
                        query = ""
                    )
                },
                onCityClick = {},
            )
        }
    }
}
