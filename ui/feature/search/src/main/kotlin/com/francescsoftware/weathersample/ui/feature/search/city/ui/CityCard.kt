package com.francescsoftware.weathersample.ui.feature.search.city.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.francescsoftware.weathersample.domain.interactor.city.api.model.City
import com.francescsoftware.weathersample.ui.feature.search.R
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.FavoriteToggle
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.InfoLabels
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginSingle
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews

@Composable
internal fun CityCard(
    city: City,
    isFavorite: Boolean,
    onClick: (City) -> Unit,
    onFavoriteClick: (City) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    Card(
        onClick = { onClick(city) },
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            InfoLabels(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = stringResource(R.string.city_result_label),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = city.name,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = MarginSingle),
                )
                Text(
                    text = stringResource(R.string.country_result_label),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = city.country,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = MarginSingle),
                )
                Text(
                    text = stringResource(R.string.coordinates_result_label),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = stringResource(
                        id = R.string.coordinates_lat_lon,
                        city.coordinates.latitude,
                        city.coordinates.longitude,
                    ),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = MarginSingle),
                )
            }
            FavoriteToggle(
                isFavorite = isFavorite,
                onClick = { onFavoriteClick(city) },
            )
        }
    }
}

@WidgetPreviews
@Composable
private fun PreviewCityCard(
    @PreviewParameter(CityStateProvider::class, 2) model: City,
) {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(all = MarginDouble),
        ) {
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
