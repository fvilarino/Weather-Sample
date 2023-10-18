package com.francescsoftware.weathersample.ui.feature.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginSingle
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews

@Composable
internal fun SwitchPreference(
    title: String,
    isChecked: Boolean,
    onCheckChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    description: String = "",
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(all = MarginDouble),
) {
    Row(
        modifier = modifier
            .clickable(enabled = enabled, onClick = { onCheckChange(!isChecked) })
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MarginSingle),
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
            )
            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth().alpha(MediumEmphasisAlpha),
                )
            }
        }
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckChange,
            enabled = enabled,
        )
    }
}

@WidgetPreviews
@Composable
private fun PreviewSwitchPreference() {
    WeatherSampleTheme {
        var isChecked by remember { mutableStateOf(false) }
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MarginDouble),
            ) {
                SwitchPreference(
                    title = "Use dynamic theming",
                    description = "",
                    isChecked = isChecked,
                    onCheckChange = { isChecked = !isChecked },
                    modifier = Modifier.fillMaxWidth(),
                )
                SwitchPreference(
                    title = "Use dynamic theming",
                    description = "Follow the colors of your wallpaper",
                    isChecked = isChecked,
                    onCheckChange = { isChecked = !isChecked },
                    modifier = Modifier.fillMaxWidth(),
                )
                SwitchPreference(
                    title = "Use dynamic theming",
                    description = "Follow the colors of your wallpaper",
                    isChecked = isChecked,
                    enabled = false,
                    onCheckChange = { isChecked = !isChecked },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
