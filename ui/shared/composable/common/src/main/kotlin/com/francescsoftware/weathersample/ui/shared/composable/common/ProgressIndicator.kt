package com.francescsoftware.weathersample.ui.shared.composable.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews

/**
 * A generic progress indicator to show indeterminate progress
 *
 * @param modifier the [Modifier] to apply to this progress indicator
 * @param label an optional prompt to show above the circular indicator
 */
@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    label: String? = null,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (label != null) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(MarginDouble))
        }
        CircularProgressIndicator()
    }
}

@WidgetPreviews
@Composable
private fun PreviewFavoritesLoadingPreview() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            ProgressIndicator(
                label = "Loading forecast",
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
