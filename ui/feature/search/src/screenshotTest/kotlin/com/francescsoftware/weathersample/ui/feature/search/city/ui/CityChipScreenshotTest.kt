package com.francescsoftware.weathersample.ui.feature.search.city.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.francescsoftware.weathersample.ui.feature.search.city.ui.CityChip
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews

class CityChipScreenshotTest {
    @SuppressLint("ComposeUnstableReceiver")
    @WidgetPreviews
    @Composable
    private fun PreviewChip() {
        WeatherSampleTheme {
            Surface {
                CityChip(
                    label = "Vancouver",
                    onClick = {},
                    onClear = {},
                    modifier = Modifier.padding(all = MarginDouble),
                )
            }
        }
    }
}
