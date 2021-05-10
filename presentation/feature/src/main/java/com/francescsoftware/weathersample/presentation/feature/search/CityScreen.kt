package com.francescsoftware.weathersample.presentation.feature.search

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.styles.MarginQuad
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.MarginTreble
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

interface CityCallbacks {
    fun onQueryChange(query: String)
    fun onCityClick(city: CityResultModel)
}

@Composable
fun CityScreen(
    viewModel: CityViewModel,
    modifier: Modifier = Modifier,
) {
    val state = viewModel.state.collectAsState()

    DisposableEffect(key1 = viewModel) {
        viewModel.onStart()
        onDispose { viewModel.onStop() }
    }
    CityScreen(
        state = state.value,
        callbacks = viewModel,
        modifier = modifier
    )
}

@Composable
private fun CityScreen(
    state: CityState,
    callbacks: CityCallbacks,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = state.query,
            onValueChange = { value -> callbacks.onQueryChange(value) },
            modifier = Modifier.fillMaxWidth(.8f).padding(top = MarginQuad),
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
                        contentDescription = null,
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(MarginTreble))
        Crossfade(targetState = state.loadState) { loadState ->
            when (loadState) {
                LoadState.IDLE -> {
                }
                LoadState.LOADING -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                LoadState.LOADED -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(360.dp),
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
                LoadState.NO_RESULTS -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.no_results_found_label),
                            style = MaterialTheme.typography.body1,
                        )
                    }
                }
                LoadState.ERROR -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.city_error_loading),
                            style = MaterialTheme.typography.body1,
                        )
                    }
                }
            }
        }
    }
}

private val noOpCityCallback = object : CityCallbacks {
    override fun onQueryChange(query: String) = Unit
    override fun onCityClick(city: CityResultModel) = Unit
}

@Preview(showBackground = true)
@Composable
private fun CityScreenPreviewDarkMode() {
    WeatherSampleTheme(darkTheme = true) {
        var state by remember {
            mutableStateOf(
                CityState(
                    loadState = LoadState.LOADED,
                    query = "",
                    cities = listOf(
                        vancouverCityModel,
                        londonCityModel,
                    )
                )
            )
        }
        Surface(modifier = Modifier
            .width(420.dp)
            .height(720.dp)) {
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

private val londonCityModel = CityResultModel(
    id = 2L,
    name = "London",
    country = "England",
    countryCode = "UK",
    coordinates = "Lat: 123.45, Lon: 1.23"
)
