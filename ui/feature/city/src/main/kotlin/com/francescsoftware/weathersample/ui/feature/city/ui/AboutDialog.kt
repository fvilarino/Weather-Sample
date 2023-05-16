package com.francescsoftware.weathersample.ui.feature.city.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francescsoftware.weathersample.ui.shared.assets.R
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews

@Composable
internal fun AboutDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.app_name)
            )
        },
        text = {
            Text(
                text = stringResource(
                    id = R.string.about_dialog_text
                )
            )
        },
        confirmButton = {
            OutlinedButton(
                onClick = onDismiss
            ) {
                Text(
                    text = stringResource(id = android.R.string.ok)
                )
            }
        },
        modifier = modifier,
    )
}

@WidgetPreviews
@Composable
private fun PreviewAboutDialog() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            AboutDialog(
                onDismiss = {},
                modifier = Modifier.padding(all = MarginDouble)
            )
        }
    }
}
