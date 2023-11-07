package com.francescsoftware.weathersample.ui.feature.search.city.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import com.francescsoftware.weathersample.ui.feature.search.R
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun CityChips(
    cities: ImmutableList<RecentCityModel>,
    singleLine: Boolean,
    onChipClick: (RecentCityModel) -> Unit,
    onDeleteChip: (RecentCityModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val cityChipsContainerTestTag = stringResource(
        id = R.string.test_tag_city_chips_container,
    )
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        if (singleLine) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(MarginDouble),
                contentPadding = PaddingValues(horizontal = MarginDouble),
                modifier = Modifier.semantics { testTag = cityChipsContainerTestTag },
            ) {
                items(
                    items = cities,
                    key = { city -> city.name },
                ) { recent ->
                    CityChip(
                        label = recent.name,
                        onClick = { onChipClick(recent) },
                        onClear = { onDeleteChip(recent) },
                        modifier = Modifier
                            .semantics { testTag = recent.name }
                            .animateItemPlacement(),
                    )
                }
            }
        } else {
            FlowRow(
                modifier = Modifier
                    .semantics { testTag = cityChipsContainerTestTag }
                    .navigationBarsPadding()
                    .verticalScroll(state = rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(MarginDouble),
                verticalArrangement = Arrangement.spacedBy(MarginDouble),
            ) {
                cities.forEach { recent ->
                    CityChip(
                        label = recent.name,
                        onClick = { onChipClick(recent) },
                        onClear = { onDeleteChip(recent) },
                        modifier = Modifier
                            .semantics { testTag = recent.name },
                    )
                }
            }
        }
    }
}

@WidgetPreviews
@Composable
private fun PreviewSingleLineCityChips() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            CityChips(
                cities = persistentListOf(
                    RecentCityModel("Barcelona"),
                    RecentCityModel("Vancouver"),
                    RecentCityModel("Munich"),
                    RecentCityModel("Prague"),
                ),
                singleLine = true,
                onChipClick = {},
                onDeleteChip = {},
                modifier = Modifier.padding(vertical = MarginDouble),
            )
        }
    }
}

@WidgetPreviews
@Composable
private fun PreviewMultiLineCityChips() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            CityChips(
                cities = persistentListOf(
                    RecentCityModel("Barcelona"),
                    RecentCityModel("Vancouver"),
                    RecentCityModel("Munich"),
                    RecentCityModel("Prague"),
                ),
                singleLine = false,
                onChipClick = {},
                onDeleteChip = {},
                modifier = Modifier.padding(vertical = MarginDouble),
            )
        }
    }
}
