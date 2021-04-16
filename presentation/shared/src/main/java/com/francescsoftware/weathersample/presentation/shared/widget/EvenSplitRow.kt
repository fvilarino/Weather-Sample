package com.francescsoftware.weathersample.presentation.shared.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

@Composable
fun EvenSplitRow(
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
        val maxLabelWidth = placeables.maxByOrNull { it.width }?.width ?: 0
        val totalWidth = maxLabelWidth * placeables.size
        val height = placeables.maxByOrNull { it.height }?.height ?: 0
        layout(
            totalWidth.coerceAtMost(constraints.maxWidth),
            height.coerceAtMost(constraints.maxHeight)
        ) {
            var xPosition = 0

            for (i in placeables.indices) {
                val placeable = placeables[i]
                 placeable.place(
                    x = xPosition + ((maxLabelWidth - placeable.width) / 2).coerceAtLeast(0),
                    y = 0
                )
                xPosition += maxLabelWidth
            }
        }
    }
}
