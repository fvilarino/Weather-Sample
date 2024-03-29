package com.francescsoftware.weathersample.ui.shared.composable.common.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme

/**
 * Displays multiple items ensuring all have the same size
 *
 * @param modifier - the [Modifier] to apply to this composable
 * @param content - the tiles to display
 */
@Composable
fun EqualSizeTiles(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
        modifier = modifier,
    ) { measurables, constraints ->
        layoutTiles(
            measurables,
            constraints,
        )
    }
}

private fun MeasureScope.layoutTiles(
    measurables: List<Measurable>,
    constraints: Constraints,
): MeasureResult {
    val tileHeight = constraints.maxHeight
    val tileWidth = if (measurables.isEmpty()) {
        0
    } else {
        measurables.maxBy { measurable ->
            measurable.maxIntrinsicWidth(tileHeight)
        }
            .maxIntrinsicWidth(tileHeight)
            .coerceAtMost(constraints.maxWidth / measurables.size)
    }
    val tileConstraints = Constraints(
        minWidth = tileWidth,
        minHeight = 0,
        maxWidth = tileWidth,
        maxHeight = constraints.maxHeight,
    )
    val placeables = measurables.map { measurable ->
        measurable.measure(tileConstraints)
    }
    val maxHeight = placeables.maxBy { it.height }.height
    val width = (placeables.size * tileWidth).coerceAtMost(constraints.maxWidth)
    return layout(width = width, height = maxHeight) {
        placeables.forEachIndexed { index, placeable ->
            placeable.place(tileWidth * index, 0)
        }
    }
}

@Preview(showBackground = true, widthDp = 512)
@Composable
private fun EqualSizeTilesPreview() {
    WeatherSampleTheme {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Yellow),
        ) {
            EqualSizeTiles(
                modifier = Modifier
                    .height(64.dp)
                    .background(color = Color.Green)
                    .padding(all = 8.dp),
            ) {
                Text(
                    text = "Left",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(color = Color.Red)
                        .padding(all = 8.dp)
                        .fillMaxHeight(),
                )
                Text(
                    text = "Center",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(color = Color.Yellow)
                        .padding(all = 8.dp)
                        .fillMaxHeight(),
                )
                Text(
                    text = "Right element",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(color = Color.Blue)
                        .padding(all = 8.dp)
                        .fillMaxHeight(),
                )
            }
        }
    }
}
