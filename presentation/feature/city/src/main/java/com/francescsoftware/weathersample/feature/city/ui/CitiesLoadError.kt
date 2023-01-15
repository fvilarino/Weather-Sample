package com.francescsoftware.weathersample.feature.city.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francescsoftware.weathersample.feature.city.R
import com.francescsoftware.weathersample.styles.PhonePreviews
import com.francescsoftware.weathersample.styles.TabletPreviews
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

@Composable
internal fun CitiesLoadError(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.city_error_loading),
            style = MaterialTheme.typography.body1,
        )
    }
}

@PhonePreviews
@TabletPreviews
@Composable
private fun CitiesLoadErrorPreview() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colors.background,
        ) {
            CitiesLoadError()
        }
    }
}
