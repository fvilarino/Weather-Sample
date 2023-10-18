package com.francescsoftware.weathersample.ui.feature.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews

@Composable
internal fun RadioRow(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(all = 0.dp),
) {
    Row(
        modifier = modifier
            .selectable(
                selected = isSelected,
                onClick = onClick,
            )
            .clickable(onClick = onClick)
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        RadioButton(
            selected = isSelected,
            onClick = onClick,
        )
    }
}

@WidgetPreviews
@Composable
fun PreviewRadioRow() {
    WeatherSampleTheme {
        var isSelected by remember { mutableStateOf(false) }
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            RadioRow(
                title = "Some option",
                isSelected = isSelected,
                onClick = { isSelected = !isSelected },
                contentPadding = PaddingValues(all = MarginDouble),
            )
        }
    }
}
