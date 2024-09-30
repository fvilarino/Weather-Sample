package com.francescsoftware.weathersample.ui.feature.search.city.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City
import com.francescsoftware.weathersample.ui.feature.search.city.ui.CityCard
import com.francescsoftware.weathersample.ui.feature.search.city.ui.CityStateProvider
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews

class CityCardScreenshotTest {
    @SuppressLint("ComposeUnstableReceiver")
    @WidgetPreviews
    @Composable
    private fun PreviewCityCard(
        @PreviewParameter(CityStateProvider::class, 2) model: City,
    ) {
        WeatherSampleTheme {
            CityCard(
                city = model,
                isFavorite = false,
                onClick = { },
                onFavoriteClick = { },
                contentPadding = PaddingValues(all = MarginDouble),
            )
        }
    }
}
