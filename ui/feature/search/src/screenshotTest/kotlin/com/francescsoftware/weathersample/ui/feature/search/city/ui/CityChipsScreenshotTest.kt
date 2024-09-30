package com.francescsoftware.weathersample.ui.feature.search.city.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.francescsoftware.weathersample.ui.feature.search.city.model.RecentCityModel
import com.francescsoftware.weathersample.ui.feature.search.city.ui.CityChips
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews
import kotlinx.collections.immutable.persistentListOf

class CityChipsScreenshotTest {
    @SuppressLint("ComposeUnstableReceiver")
    @WidgetPreviews
    @Composable
    private fun PreviewMultiLineCityChips() {
        WeatherSampleTheme {
            Surface {
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
                    modifier = Modifier.padding(all = MarginDouble),
                )
            }
        }
    }
}
