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
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import com.francescsoftware.weathersample.ui.feature.search.city.viewmodel.CityState
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun CityChips(
    state: CityState,
    onChipClick: (RecentCityModel) -> Unit,
    onDeleteChip: (RecentCityModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MarginDouble),
        ) {
            items(
                items = state.recentCities,
                key = { city -> city.name },
            ) { recent ->
                CityChip(
                    label = recent.name,
                    onClick = { onChipClick(recent) },
                    onClear = { onDeleteChip(recent) },
                    modifier = Modifier.animateItemPlacement(),
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
                state = CityState.initial.copy(
                    recentCities = persistentListOf(
                        RecentCityModel("Barcelona"),
                        RecentCityModel("Vancouver"),
                        RecentCityModel("Munich"),
                        RecentCityModel("Prague"),
                    ),
                    showRecentCities = true,
                ),
                onChipClick = {},
                onDeleteChip = {},
                modifier = Modifier.padding(vertical = MarginDouble),
            )
        }
    }
}
