package com.francescsoftware.weathersample.presentation.shared.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
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
