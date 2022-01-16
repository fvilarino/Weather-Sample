package com.francescsoftware.weathersample.shared.composable

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import kotlin.math.max

@Composable
fun InfoLabels(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
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
        val height = List(labels.size) { index ->
            max(labels[index].height, descriptions[index].height)
        }.sum()
        layout(
            constraints.maxWidth,
            height.coerceAtMost(constraints.maxHeight)
        ) {
            var yPosition = 0

            for (i in labels.indices) {
                val label = labels[i]
                val description = descriptions[i]
                label.placeRelative(
                    x = 0,
                    y = yPosition
                )
                description.placeRelative(
                    x = maxLabelWidth,
                    y = yPosition
                )
                yPosition += label.height.coerceAtLeast(description.height)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, widthDp = 320)
@Composable
private fun InfoLabelsPreview() {
    WeatherSampleTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            InfoLabels {
                Text(
                    text = "Short:",
                    style = MaterialTheme.typography.body2,
                )
                Text(
                    text = "Short description",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(start = 8.dp),
                )
                Text(
                    text = "Long label:",
                    style = MaterialTheme.typography.body2,
                )
                Text(
                    text = "Long description lorem ipsum dolor sit amet",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
        }
    }
}
