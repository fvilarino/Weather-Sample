@file:Suppress("MatchingDeclarationName")

package com.francescsoftware.weathersample.ui.shared.composable.common.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import kotlin.math.roundToInt

/** The [DualPane] panes orientation, controlling the stacking of the panes and their relative aspect ratios */
class PanesOrientation private constructor(
    internal val orientation: Orientation,
) {
    /**
     * Panes orientation
     *
     * @property aspectRatio the relative aspect ratio between panes
     */
    internal sealed interface Orientation {
        val aspectRatio: Float

        /**
         * Align the panes horizontally
         *
         * @property aspectRatio the relative aspect ratio between panes
         */
        data class Horizontal(override val aspectRatio: Float) : Orientation

        /**
         * Align the panes vertically
         *
         * @property aspectRatio the relative aspect ratio between panes
         */
        data class Vertical(override val aspectRatio: Float) : Orientation
    }

    companion object {
        /**
         * Builder method for the horizontal panes configuration
         *
         * @param aspectRatio the relative aspect ratio between panes
         * @return a [PanesOrientation] to configure dual panes horizontally
         */
        fun horizontal(aspectRatio: Float): PanesOrientation {
            check(aspectRatio in 0f..1f) { "Aspect ratio must be in the [0f..1f] range" }
            return PanesOrientation(
                orientation = Orientation.Horizontal(aspectRatio),
            )
        }

        /**
         * Builder method for the vertical panes configuration
         *
         * @param aspectRatio the relative aspect ratio between panes
         * @return a [PanesOrientation] to configure dual panes vertically
         */
        fun vertical(aspectRatio: Float): PanesOrientation {
            check(aspectRatio in 0f..1f) { "Aspect ratio must be in the [0f..1f] range" }
            return PanesOrientation(
                orientation = Orientation.Vertical(aspectRatio),
            )
        }
    }
}

/**
 * A composable displaying 2 children either horizontally or vertically based on available space
 *
 * @param panesOrientation the [PanesOrientation] to lay the panes in
 * @param paneOne left or top pane content
 * @param paneTwo right or bottom pane content
 * @param modifier the [Modifier] to apply to this composable
 */
@Composable
fun DualPane(
    panesOrientation: PanesOrientation,
    paneOne: @Composable () -> Unit,
    paneTwo: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Layout(
        content = {
            paneOne()
            paneTwo()
        },
        modifier = modifier,
    ) { measurables, constraints ->
        val firstPaneConstraints = when (panesOrientation.orientation) {
            is PanesOrientation.Orientation.Horizontal -> Constraints.fixed(
                width = (constraints.maxWidth * panesOrientation.orientation.aspectRatio).roundToInt(),
                height = constraints.maxHeight
            )

            is PanesOrientation.Orientation.Vertical -> Constraints.fixed(
                width = constraints.maxWidth,
                height = (constraints.maxHeight * panesOrientation.orientation.aspectRatio).roundToInt()
            )
        }
        val paneOnePlaceable = measurables[0].measure(firstPaneConstraints)
        val secondPaneConstraints = when (panesOrientation.orientation) {
            is PanesOrientation.Orientation.Horizontal -> Constraints.fixed(
                width = (constraints.maxWidth * (1f - panesOrientation.orientation.aspectRatio)).roundToInt(),
                height = constraints.maxHeight
            )

            is PanesOrientation.Orientation.Vertical -> Constraints.fixed(
                width = constraints.maxWidth,
                height = (constraints.maxHeight * (1f - panesOrientation.orientation.aspectRatio)).roundToInt()
            )
        }
        val paneTwoPlaceable = measurables[1].measure(secondPaneConstraints)
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
            paneOnePlaceable.placeRelative(
                x = 0,
                y = 0,
            )
            paneTwoPlaceable.placeRelative(
                x = when (panesOrientation.orientation) {
                    is PanesOrientation.Orientation.Horizontal -> paneOnePlaceable.width
                    is PanesOrientation.Orientation.Vertical -> 0
                },
                y = when (panesOrientation.orientation) {
                    is PanesOrientation.Orientation.Horizontal -> 0
                    is PanesOrientation.Orientation.Vertical -> paneOnePlaceable.height
                },
            )
        }
    }
}

@Preview(device = Devices.PIXEL_3A_XL, group = "Phone")
@Composable
private fun PreviewHorizontalDualPane() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            DualPane(
                panesOrientation = PanesOrientation.horizontal(aspectRatio = .5f),
                paneOne = { Box(modifier = Modifier.background(Color.Red)) },
                paneTwo = { Box(modifier = Modifier.background(Color.Blue)) },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(device = Devices.PIXEL_3A_XL, group = "Phone")
@Composable
private fun PreviewVerticalDualPane() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            DualPane(
                panesOrientation = PanesOrientation.vertical(aspectRatio = .33f),
                paneOne = { Box(modifier = Modifier.background(Color.Red)) },
                paneTwo = { Box(modifier = Modifier.background(Color.Blue)) },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
