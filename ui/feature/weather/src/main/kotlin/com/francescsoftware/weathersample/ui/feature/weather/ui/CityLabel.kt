package com.francescsoftware.weathersample.ui.feature.weather.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.francescsoftware.weathersample.ui.feature.weather.R
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews

@Composable
internal fun CityLabel(
    cityName: String,
    countryCode: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.weather_city_label),
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = stringResource(
                id = R.string.weather_city_name,
                cityName,
                countryCode,
            ),
            modifier = Modifier.padding(horizontal = MarginDouble),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@WidgetPreviews
@Composable
private fun PreviewCityLabel() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            CityLabel(
                cityName = "Vancouver",
                countryCode = "CA",
                modifier = Modifier.fillMaxWidth().padding(all = MarginDouble),
            )
        }
    }
}
