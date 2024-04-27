package com.francescsoftware.weathersample.ui.feature.search.city.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francescsoftware.weathersample.ui.feature.search.R
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews

@Composable
internal fun CityChip(
    label: String,
    onClick: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AssistChip(
        onClick = onClick,
        label = {
            Text(
                text = label,
                // remove after https://issuetracker.google.com/issues/325934990 is fixed
                modifier = Modifier.padding(top = MarginDouble),
            )
        },
        trailingIcon = {
            IconButton(
                onClick = onClear,
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.content_description_remove_chip),
                )
            }
        },
        modifier = modifier,
    )
}

@WidgetPreviews
@Composable
private fun PreviewChip() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                CityChip(
                    label = "Vancouver",
                    onClick = {},
                    onClear = {},
                    modifier = Modifier.padding(vertical = MarginDouble),
                )
                CityChip(
                    label = "Barcelona",
                    onClick = {},
                    onClear = {},
                    modifier = Modifier.padding(vertical = MarginDouble),
                )
            }
        }
    }
}
