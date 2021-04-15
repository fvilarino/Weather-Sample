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
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import kotlin.math.max

@Composable
fun CityCard(
    city: CityResultModel,
    onClick: (CityResultModel) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    Card(modifier = modifier.clickable(onClick = { onClick(city) })) {
        City(
            modifier = Modifier.padding(contentPadding),
            content = {
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
        )
    }
}

@Composable
private fun City(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
        Layout(
            content = content,
            modifier = modifier,
        ) { measurables, constraints ->
            val looseConstraints = constraints.copy(
                minWidth = 0,
                minHeight = 0,
            )
            val placeables = measurables.map { measurable ->
                measurable.measure(looseConstraints)
            }
            val labels = List(placeables.size / 2) { index ->
                placeables[2 * index]
            }
            val descriptions = List(placeables.size / 2) { index ->
                placeables[2 * index + 1]
            }
            val maxLabelWidth = labels.maxByOrNull { it.width }?.width ?: 0
            val width = List(labels.size) { index ->
                maxLabelWidth + descriptions[index].width
            }.maxOrNull() ?: 0
            val height = List(labels.size) { index ->
                max(labels[index].height, descriptions[index].height)
            }.sum()
            layout(
                width.coerceAtMost(constraints.maxWidth),
                height.coerceAtMost(constraints.maxHeight)
            ) {
                var yPosition = 0

                for (i in labels.indices) {
                    val label = labels[i]
                    val description = descriptions[i]
                    val labelHeight = label.height
                    val descriptionHeight = description.height
                    label.place(
                        x = 0,
                        y = yPosition + (descriptionHeight - labelHeight).coerceAtLeast(0)
                    )
                    description.place(
                        x = maxLabelWidth,
                        y = yPosition + (labelHeight - descriptionHeight).coerceAtLeast(0)
                    )
                    yPosition += label.height.coerceAtLeast(description.height)
                }
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
        Surface(

        ) {
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
