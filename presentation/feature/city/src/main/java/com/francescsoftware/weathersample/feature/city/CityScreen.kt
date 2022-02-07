package com.francescsoftware.weathersample.feature.city

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
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
import com.francescsoftware.weathersample.styles.MarginQuad
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.MarginTreble
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

private val MinColumnWidth = 360.dp

interface CityCallbacks {
    fun onQueryChange(query: String)
    fun onCityClick(city: CityResultModel)
}

@Composable
fun CityScreen(
    viewModel: CityViewModel,
    modifier: Modifier = Modifier,
) {
    CityScreen(
        state = viewModel.state.value,
        callbacks = viewModel,
        modifier = modifier,
    )
}

@Composable
private fun CityScreen(
    state: CityState,
    callbacks: CityCallbacks,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CitiesSearchBox(state, callbacks)
        Crossfade(
            targetState = state.loadState,
            modifier = modifier.padding(top = MarginTreble),
        ) { loadState ->
            when (loadState) {
                LoadState.IDLE -> {
                }
                LoadState.LOADING -> CitiesLoading(
                    modifier = Modifier.fillMaxSize(),
                )
                LoadState.LOADED -> CitiesList(
                    state = state,
                    callbacks = callbacks,
                    modifier = Modifier.fillMaxSize(),
                )
                LoadState.NO_RESULTS -> CitiesNoResults(
                    modifier = Modifier.fillMaxSize(),
                )
                LoadState.ERROR -> CitiesLoadError(
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
private fun CitiesSearchBox(
    state: CityState,
    callbacks: CityCallbacks,
) {
    OutlinedTextField(
        value = state.query,
        onValueChange = { value -> callbacks.onQueryChange(value) },
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
            IconButton(onClick = { callbacks.onQueryChange("") }) {
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
    callbacks: CityCallbacks,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
    ) {
        LazyVerticalGrid(
            cells = GridCells.Adaptive(
                minSize = MinColumnWidth,
            ),
            modifier = Modifier
                .width(MinColumnWidth)
                .fillMaxHeight(),
            contentPadding = PaddingValues(bottom = MarginSingle),
        ) {
            items(state.cities) { city ->
                CityCard(
                    city = city,
                    onClick = { model -> callbacks.onCityClick(model) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = MarginSingle),
                    contentPadding = PaddingValues(all = MarginSingle),
                )
            }
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

@Preview(group = "city", showBackground = true, widthDp = 420, heightDp = 720)
@Preview(
    group = "city",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 420,
    heightDp = 720
)
@Preview(group = "city", device = Devices.PIXEL_C)
@Composable
private fun CityScreenPreviewDarkMode() {
    WeatherSampleTheme {
        var state by remember {
            mutableStateOf(
                CityState(
                    loadState = LoadState.LOADED,
                    query = "",
                    cities = listOf(
                        vancouverCityModel,
                        barcelonaCityModel,
                        londonCityModel,
                    )
                )
            )
        }
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            CityScreen(
                state = state,
                callbacks = object : CityCallbacks {
                    override fun onQueryChange(query: String) {
                        state = state.copy(query = query)
                    }

                    override fun onCityClick(city: CityResultModel) = Unit
                },
            )
        }
    }
}

private val vancouverCityModel = CityResultModel(
    id = 1L,
    name = "Vancouver",
    country = "Canada",
    countryCode = "CA",
    coordinates = "Lat: 49.26, Lon: -123.11"
)

private val barcelonaCityModel = CityResultModel(
    id = 2L,
    name = "Barcelona",
    country = "Spain",
    countryCode = "ES",
    coordinates = "Lat: 41.39, Lon: 2.17"
)

private val londonCityModel = CityResultModel(
    id = 3L,
    name = "London",
    country = "England",
    countryCode = "UK",
    coordinates = "Lat: 123.45, Lon: 1.23"
)
