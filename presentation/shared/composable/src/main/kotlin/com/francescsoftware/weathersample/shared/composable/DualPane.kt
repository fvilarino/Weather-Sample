package com.francescsoftware.weathersample.shared.composable

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Constraints
import com.francescsoftware.weathersample.deviceclass.DeviceClass
import com.francescsoftware.weathersample.deviceclass.DeviceClassProvider
import com.francescsoftware.weathersample.styles.PhonePreviews
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import kotlin.math.roundToInt

private const val HorizontalPanesRatio = 1f / 3f

@Composable
fun DualPane(
    paneOne: @Composable () -> Unit,
    paneTwo: @Composable () -> Unit,
    deviceClass: DeviceClass,
    modifier: Modifier = Modifier,
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val useHorizontalPanes = isLandscape && deviceClass == DeviceClass.Compact
    Layout(
        content = {
            paneOne()
            paneTwo()
        },
        modifier = modifier,
    ) { measurables, constraints ->
        val firstPaneConstraints = if (useHorizontalPanes) {
            Constraints.fixed(
                width = (constraints.maxWidth * HorizontalPanesRatio).roundToInt(),
                height = constraints.maxHeight
            )
        } else {
            constraints.copy(
                minWidth = 0,
                minHeight = 0,
            )
        }
        val secondPaneConstraints = if (useHorizontalPanes) {
            Constraints.fixed(
                width = (constraints.maxWidth * (1f - HorizontalPanesRatio)).roundToInt(),
                height = constraints.maxHeight
            )
        } else {
            constraints.copy(
                minWidth = 0,
                minHeight = 0,
            )
        }
        val paneOnePlaceable = measurables[0].measure(firstPaneConstraints)
        val paneTwoPlaceable = measurables[1].measure(secondPaneConstraints)
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
            paneOnePlaceable.placeRelative(
                x = 0,
                y = 0,
            )
            paneTwoPlaceable.placeRelative(
                x = if (useHorizontalPanes) paneOnePlaceable.width else 0,
                y = if (useHorizontalPanes) 0 else paneOnePlaceable.height,
            )
        }
    }
}

@PhonePreviews
@Composable
private fun DualPanePreview(
    @PreviewParameter(DeviceClassProvider::class) deviceClass: DeviceClass,
) {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            DualPane(
                paneOne = { Box(modifier = Modifier.background(Color.Red)) },
                paneTwo = { Box(modifier = Modifier.background(Color.Blue)) },
                deviceClass = deviceClass,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
