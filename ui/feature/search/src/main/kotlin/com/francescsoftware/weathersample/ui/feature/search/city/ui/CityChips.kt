package com.francescsoftware.weathersample.ui.feature.search.city.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.francescsoftware.weathersample.ui.feature.search.R
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun CityChips(
    cities: ImmutableList<RecentCityModel>,
    onChipClick: (RecentCityModel) -> Unit,
    onDeleteChip: (RecentCityModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val cityChipsContainerContentDescription = stringResource(
        id = R.string.content_description_city_chips_container
    )
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MarginDouble),
            modifier = Modifier.semantics { contentDescription = cityChipsContainerContentDescription },
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
                        .semantics { contentDescription = recent.name }
                        .animateItemPlacement(),
                )
            }
        }
    }
}

@WidgetPreviews
@Composable
private fun PreviewCityChips() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            CityChips(
                cities = persistentListOf(
                    RecentCityModel("Barcelona"),
                    RecentCityModel("Vancouver"),
                    RecentCityModel("Munich"),
                    RecentCityModel("Prague"),
                ),
                onChipClick = {},
                onDeleteChip = {},
                modifier = Modifier.padding(vertical = MarginDouble),
            )
        }
    }
}
