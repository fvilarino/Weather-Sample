package com.francescsoftware.weathersample.feature.city.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.francescsoftware.weathersample.styles.PhonePreviews
import com.francescsoftware.weathersample.styles.TabletPreviews
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

@Composable
internal fun CitiesLoading(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@PhonePreviews
@TabletPreviews
@Composable
fun CitiesLoadingPreview() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colors.background,
        ) {
            CitiesLoading()
        }
    }
}
