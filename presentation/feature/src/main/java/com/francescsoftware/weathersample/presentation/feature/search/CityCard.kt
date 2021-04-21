package com.francescsoftware.weathersample.presentation.feature.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.presentation.shared.widget.InfoLabels
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

@Composable
fun CityCard(
    city: CityResultModel,
    onClick: (CityResultModel) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    Card(modifier = modifier.clickable(onClick = { onClick(city) })) {
        InfoLabels(
            modifier = Modifier.padding(contentPadding)
        ) {
            Text(
                text = stringResource(R.string.city_result_label),
                style = MaterialTheme.typography.body2,
            )
            Text(
                text = city.name.toString(),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(start = MarginSingle),
            )
            Text(
                text = stringResource(R.string.country_result_label),
                style = MaterialTheme.typography.body2,
            )
            Text(
                text = city.country.toString(),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(start = MarginSingle),
            )
            Text(
                text = stringResource(R.string.coordinates_result_label),
                style = MaterialTheme.typography.body2,
            )
            Text(
                text = city.coordinates.toString(),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(start = MarginSingle),
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewCityCard() {
    val card = CityResultModel(
        id = 1L,
        name = "Vancouver",
        country = "Canada",
        countryCode = "CA",
        coordinates = "Lat: 49.26, Lon: -123.11"
    )
    WeatherSampleTheme {
        Surface {
            CityCard(
                city = card,
                onClick = { },
                modifier = Modifier.padding(all = 8.dp),
                contentPadding = PaddingValues(all = 16.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCityCardDarkMode() {
    val card = CityResultModel(
        id = 1L,
        name = "Vancouver",
        country = "Canada",
        countryCode = "CA",
        coordinates = "Lat: 49.26, Lon: -123.11"
    )
    WeatherSampleTheme(darkTheme = true) {
        Surface {
            CityCard(
                city = card,
                onClick = { },
                modifier = Modifier.padding(all = 8.dp),
                contentPadding = PaddingValues(all = 16.dp),
            )
        }
    }
}
