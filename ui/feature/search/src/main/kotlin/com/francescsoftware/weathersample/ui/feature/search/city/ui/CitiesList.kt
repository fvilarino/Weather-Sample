package com.francescsoftware.weathersample.ui.feature.search.city.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.francescsoftware.weathersample.ui.feature.search.city.model.CityResultModel
import com.francescsoftware.weathersample.ui.feature.search.navigation.SelectedCity
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginSingle
import com.francescsoftware.weathersample.ui.shared.styles.PhonePreviews
import com.francescsoftware.weathersample.ui.shared.styles.TabletPreviews
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun CitiesList(
    cities: ImmutableList<CityResultModel>,
    onCityClick: (SelectedCity) -> Unit,
    onFavoriteClick: (CityResultModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(
            minSize = MinColumnWidth,
        ),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MarginDouble),
        verticalArrangement = Arrangement.spacedBy(MarginDouble),
        contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
    ) {
        items(cities) { city ->
            CityCard(
                city = city,
                onClick = { model -> onCityClick(model.toSelectedCity()) },
                onFavoriteClick = onFavoriteClick,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(all = MarginSingle),
            )
        }
    }
}

private fun CityResultModel.toSelectedCity() = SelectedCity(
    name = name,
    countryCode = countryCode,
)

@PhonePreviews
@TabletPreviews
@Composable
private fun PreviewCitiesList() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            CitiesList(
                cities = persistentListOf(
                    VancouverCityModel,
                    BarcelonaCityModel,
                    LondonCityModel,
                ),
                onCityClick = { },
                onFavoriteClick = { },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MarginDouble),
            )
        }
    }
}
