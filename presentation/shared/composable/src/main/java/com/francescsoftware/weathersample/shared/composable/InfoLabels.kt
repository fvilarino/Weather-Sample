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
                val height = max(label.height, description.height)
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
