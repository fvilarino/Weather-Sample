package com.francescsoftware.weathersample.feature.city.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.francescsoftware.weathersample.feature.city.model.RecentCityModel
import com.francescsoftware.weathersample.feature.city.viewmodel.CityState
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.styles.WidgetPreviews

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
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(MarginDouble)
        ) {
            state.recentCities.forEach { recent ->
                CityChip(
                    label = recent.name,
                    onClick = { onChipClick(recent) },
                    onClear = { onDeleteChip(recent) },
                )
            }
        }
    }
}

@WidgetPreviews
@Composable
private fun CityChipsPreview() {
    WeatherSampleTheme {
        CityChips(
            state = CityState.initial.copy(
                recentCities = listOf(
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
