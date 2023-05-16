package com.francescsoftware.weathersample.ui.feature.favorites.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francescsoftware.weathersample.ui.feature.favorites.R
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews

@Composable
internal fun CityNameLabel(
    name: String,
    countryCode: String,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(
                id = R.string.favorite_city_name,
                name,
                countryCode,
            ),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = MarginDouble),
            style = MaterialTheme.typography.headlineSmall,
        )
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.align(Alignment.CenterEnd),
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.content_description_delete_favorite)
            )
        }
    }
}

@WidgetPreviews
@Composable
private fun PreviewCityNameLabel() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            CityNameLabel(
                name = "Vancouver",
                countryCode = "CA",
                onDeleteClick = { },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
