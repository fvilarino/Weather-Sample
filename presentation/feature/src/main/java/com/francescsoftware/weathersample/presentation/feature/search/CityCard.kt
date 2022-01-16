package com.francescsoftware.weathersample.presentation.feature.search

import android.content.res.Configuration
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
import com.francescsoftware.weathersample.shared.composable.InfoLabels
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

@Composable
fun CityCard(
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

@Preview(showBackground = true, widthDp = 360)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, widthDp = 360)
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
