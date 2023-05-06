package com.francescsoftware.weathersample.shared.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.styles.WidgetPreviews
import kotlin.math.max

/**
 * Displays pairs of label + content ensuring that all labels are of the same length.
 *
 * @param modifier - the [Modifier] to apply to this composable
 * @param content - a list of composables, listed as label, content, label, content to display
 */
@Composable
fun InfoLabels(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
        modifier = modifier,
    ) { measurables, constraints ->
        check(measurables.size % 2 == 0) { "This composable expects pairs of children" }
        val numPairs = measurables.size / 2
        val looseConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0,
        )
        val labelMeasurables = measurables.slice(measurables.indices step 2)
        val descriptionMeasurables = measurables.slice(1..measurables.lastIndex step 2)
        val labelPlaceables = labelMeasurables.map { measurable -> measurable.measure(looseConstraints) }
        val labelWidth = labelPlaceables.maxBy { placeable -> placeable.width }.width
        val descriptionConstraints = looseConstraints.copy(
            minWidth = constraints.maxWidth - labelWidth,
            maxWidth = constraints.maxWidth - labelWidth,
        )
        val descriptionPlaceables = descriptionMeasurables.map { measurable ->
            measurable.measure(descriptionConstraints)
        }
        val requiredHeight = (0 until numPairs).sumOf { index ->
            max(
                labelPlaceables[index].height,
                descriptionPlaceables[index].height,
            )
        }
        layout(
            constraints.maxWidth,
            requiredHeight.coerceAtMost(constraints.maxHeight),
        ) {
            var yPosition = 0

            for (i in 0 until numPairs) {
                val label = labelPlaceables[i]
                val description = descriptionPlaceables[i]
                label.placeRelative(
                    x = 0,
                    y = yPosition,
                )
                description.placeRelative(
                    x = labelWidth,
                    y = yPosition,
                )
                yPosition += max(label.height, description.height)
            }
        }
    }
}

@WidgetPreviews
@Composable
private fun InfoLabelsPreview() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            InfoLabels {
                Text(
                    text = "Short:",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Short description",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp),
                )
                Text(
                    text = "Long label:",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Long description lorem ipsum dolor sit amet",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
        }
    }
}
