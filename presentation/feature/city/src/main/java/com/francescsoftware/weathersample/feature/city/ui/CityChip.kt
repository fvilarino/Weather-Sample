package com.francescsoftware.weathersample.feature.city.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.styles.WidgetPreviews

@Composable
fun CityChip(
    label: String,
    onClick: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colors.onPrimary,
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
) {
    Chip(
        onClick = onClick,
        colors = ChipDefaults.chipColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor,
        ),
        modifier = modifier,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(horizontal = MarginDouble),
            color = contentColor,
        )
        IconButton(
            onClick = onClear,
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                tint = contentColor,
                contentDescription = null,
            )
        }
    }
}

@WidgetPreviews
@Composable
private fun ChipPreview() {
    WeatherSampleTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CityChip(
                label = "One",
                onClick = {},
                onClear = {},
                modifier = Modifier.padding(vertical = MarginDouble),
            )
            CityChip(
                label = "Two",
                onClick = {},
                onClear = {},
                modifier = Modifier.padding(vertical = MarginDouble),
                contentColor = Color.White,
                backgroundColor = Color.Red,
            )
        }
    }
}
