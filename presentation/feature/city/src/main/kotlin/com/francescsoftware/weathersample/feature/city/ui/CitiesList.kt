package com.francescsoftware.weathersample.feature.city.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.francescsoftware.weathersample.feature.city.model.CityResultModel
import com.francescsoftware.weathersample.feature.city.viewmodel.CityState
import com.francescsoftware.weathersample.feature.city.viewmodel.LoadState
import com.francescsoftware.weathersample.presentation.route.SelectedCity
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.PhonePreviews
import com.francescsoftware.weathersample.styles.TabletPreviews
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

@Composable
internal fun CitiesList(
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
        contentPadding = PaddingValues(
            start = MarginDouble,
            end = MarginDouble,
            top = MarginDouble,
        )
    ) {
        items(state.cities) { city ->
            CityCard(
                city = city,
                onClick = { model -> onCityClick(model.toSelectedCity()) },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(all = MarginSingle),
            )
        }
        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
        }
    }
}

private fun CityResultModel.toSelectedCity() = SelectedCity(
    name = name.toString(),
    country = country.toString(),
    countryCode = countryCode,
)

@PhonePreviews
@TabletPreviews
@Composable
private fun CitiesListPreview() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            CitiesList(
                state = CityState.initial.copy(
                    loadState = LoadState.Loaded,
                    cities = listOf(
                        VancouverCityModel,
                        BarcelonaCityModel,
                        LondonCityModel
                    ),
                ),
                onCityClick = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MarginDouble),
            )
        }
    }
}
