package com.francescsoftware.weathersample.feature.city.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.francescsoftware.weathersample.feature.city.R
import com.francescsoftware.weathersample.feature.city.model.CityResultModel
import com.francescsoftware.weathersample.shared.composable.InfoLabels
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.styles.WidgetPreviews

@Composable
internal fun CityCard(
    city: CityResultModel,
    onClick: (CityResultModel) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    Card(
        onClick = { onClick(city) },
        modifier = modifier,
    ) {
        InfoLabels(
            modifier = Modifier.padding(contentPadding)
        ) {
            Text(
                text = stringResource(R.string.city_result_label),
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = city.name.toString(),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = MarginSingle),
            )
            Text(
                text = stringResource(R.string.country_result_label),
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = city.country.toString(),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = MarginSingle),
            )
            Text(
                text = stringResource(R.string.coordinates_result_label),
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = city.coordinates.toString(),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = MarginSingle),
            )
        }
    }
}

@WidgetPreviews
@Composable
internal fun PreviewCityCard(
    @PreviewParameter(CityStateProvider::class, 2) model: CityResultModel,
) {
    WeatherSampleTheme {
        CityCard(
            city = model,
            onClick = { },
            contentPadding = PaddingValues(all = MarginDouble),
        )
    }
}
