package com.francescsoftware.weathersample.presentation.feature.weather

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.WeatherSampleTheme

enum class WeatherSelectorOptions {
    Today,
    Forecast,
}

@Composable
fun WeatherSelector(
    selectedOption: WeatherSelectorOptions,
    onOptionSelect: (WeatherSelectorOptions) -> Unit,
    modifier: Modifier = Modifier
) {
    val transition = updateTransition(selectedOption, label = "selectorColor")
    val leftBackgroundColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = 300) }, label = "leftColorSpec"
    ) { state ->
        when (state) {
            WeatherSelectorOptions.Today -> MaterialTheme.colors.secondary
            WeatherSelectorOptions.Forecast -> MaterialTheme.colors.surface
        }
    }
    val rightTextColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = 300) }, label = "leftTextColorSpec"
    ) { state ->
        when (state) {
            WeatherSelectorOptions.Today -> MaterialTheme.colors.onSurface
            WeatherSelectorOptions.Forecast -> MaterialTheme.colors.onSecondary
        }
    }
    val rightBackgroundColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = 300) }, label = "rightColorSpec"
    ) { state ->
        when (state) {
            WeatherSelectorOptions.Today -> MaterialTheme.colors.surface
            WeatherSelectorOptions.Forecast -> MaterialTheme.colors.secondary
        }
    }
    val leftTextColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = 300) }, label = "rightTextColorSpec"
    ) { state ->
        when (state) {
            WeatherSelectorOptions.Today -> MaterialTheme.colors.onSecondary
            WeatherSelectorOptions.Forecast -> MaterialTheme.colors.onSurface
        }
    }

    Row(
        modifier = modifier.selectableGroup()
    ) {
        Text(
            text = stringResource(id = R.string.today_weather_button_label),
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .weight(1f)
                .clip(
                    RoundedCornerShape(
                        topStartPercent = 50,
                        bottomStartPercent = 50,
                    )
                )
                .background(leftBackgroundColor)
                .selectable(
                    selected = selectedOption == WeatherSelectorOptions.Today,
                    onClick = { onOptionSelect(WeatherSelectorOptions.Today) },
                    role = Role.RadioButton,
                )
                .padding(vertical = MarginSingle),
            textAlign = TextAlign.Center,
            color = leftTextColor,
        )
        Text(
            text = stringResource(id = R.string.forecast_weather_button_label),
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .weight(1f)
                .clip(
                    RoundedCornerShape(
                        topEndPercent = 50,
                        bottomEndPercent = 50,
                    )
                )
                .selectable(
                    selected = selectedOption == WeatherSelectorOptions.Forecast,
                    onClick = { onOptionSelect(WeatherSelectorOptions.Forecast) },
                    role = Role.RadioButton,
                )
                .background(rightBackgroundColor)
                .padding(vertical = MarginSingle),
            textAlign = TextAlign.Center,
            color = rightTextColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewWeatherSelector() {
    WeatherSampleTheme {
        var option by remember {
            mutableStateOf(WeatherSelectorOptions.Today)
        }
        Surface(modifier = Modifier
            .width(256.dp)
            .padding(all = MarginSingle)) {
            WeatherSelector(
                selectedOption = WeatherSelectorOptions.Today,
                onOptionSelect = { selected ->
                    option = selected
                },
            )
        }
    }
}
