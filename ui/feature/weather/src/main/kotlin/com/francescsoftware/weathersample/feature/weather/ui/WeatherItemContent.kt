package com.francescsoftware.weathersample.feature.weather.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.styles.WidgetPreviews

@Composable
internal fun WeatherItemContent(
    label: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = label,
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier,
    )
}

@Composable
internal fun WeatherItemContent(
    label: AnnotatedString,
    modifier: Modifier = Modifier,
) {
    Text(
        text = label,
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier,
    )
}

@WidgetPreviews
@Composable
private fun PreviewWeatherItemContent() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MarginDouble)
            ) {
                WeatherItemContent(
                    label = "Just a weather label",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = MarginDouble),
                )
                WeatherItemContent(
                    label = buildAnnotatedString {
                        append("Just a ")
                        withStyle(SpanStyle(color = Color.Red)) {
                            append("weather")
                        }
                        append(" label")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = MarginDouble),
                )
            }
        }
    }
}
