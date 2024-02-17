package com.francescsoftware.weathersample.ui.shared.composable.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.core.type.weather.AverageVisibility
import com.francescsoftware.weathersample.core.type.weather.Humidity
import com.francescsoftware.weathersample.core.type.weather.Precipitation
import com.francescsoftware.weathersample.core.type.weather.Speed
import com.francescsoftware.weathersample.core.type.weather.Temperature
import com.francescsoftware.weathersample.core.type.weather.UvIndex
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.InfoLabels
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginSingle
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews

private val HeaderIconSize = 64.dp

/**
 * Displays a weather forecast
 *
 * @param state the state representing this forecast
 * @param modifier the [Modifier] to be applied to the weather forecast
 */
@Composable
fun ForecastWeather(
    state: ForecastHourState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                painter = painterResource(id = state.iconId),
                contentDescription = null,
                modifier = Modifier.size(HeaderIconSize),
            )
            Text(
                text = state.description,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = MarginDouble)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = state.time,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End,
                modifier = Modifier.width(HeaderIconSize),
                maxLines = 1,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            InfoLabels(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = MarginSingle),
            ) {
                WeatherItemLabel(
                    label = stringResource(id = R.string.temperature_label),
                )
                WeatherItemContent(
                    label = state.temperature.format(),
                    modifier = Modifier.padding(start = MarginSingle),
                )
                WeatherItemLabel(
                    label = stringResource(id = R.string.feels_like_label),
                )
                WeatherItemContent(
                    label = state.feelsLikeTemperature.format(),
                    modifier = Modifier.padding(start = MarginSingle),
                )
                WeatherItemLabel(
                    label = stringResource(id = R.string.precipitation_label),
                )
                WeatherItemContent(
                    label = state.precipitation.format(),
                    modifier = Modifier.padding(start = MarginSingle),
                )
            }
            InfoLabels(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = MarginSingle),
            ) {
                WeatherItemLabel(
                    label = stringResource(id = R.string.wind_speed_label),
                )
                WeatherItemContent(
                    label = state.windSpeed.format(),
                    modifier = Modifier.padding(start = MarginSingle),
                )
                WeatherItemLabel(
                    label = stringResource(id = R.string.humidity_label),
                )
                WeatherItemContent(
                    label = state.humidity.format(),
                    modifier = Modifier.padding(start = MarginSingle),
                )
                WeatherItemLabel(
                    label = stringResource(id = R.string.visibility_label),
                )
                WeatherItemContent(
                    label = state.visibility.format(),
                    modifier = Modifier.padding(start = MarginSingle),
                )
            }
        }
    }
}

@Suppress("MagicNumber")
private val SunnyHourForecast = ForecastHourState(
    id = 2L,
    time = "14:00",
    description = "Sunny",
    iconId = com.francescsoftware.weathersample.ui.shared.assets.R.drawable.ic_sunny,
    temperature = Temperature.fromCelsius(18.7),
    feelsLikeTemperature = Temperature.fromCelsius(17.5),
    precipitation = Precipitation.fromMillimeters(10.0),
    uvIndex = UvIndex(0),
    windSpeed = Speed.fromKph(14.1),
    humidity = Humidity(75),
    visibility = AverageVisibility.fromKm(10.0),
)

private val RainyHourForecast = SunnyHourForecast.copy(
    description = "Patchy rain possible",
    iconId = com.francescsoftware.weathersample.ui.shared.assets.R.drawable.ic_light_rain,
)

@WidgetPreviews
@Composable
private fun ForecastWeatherCardPreview() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MarginDouble),
            ) {
                ForecastWeather(
                    state = SunnyHourForecast,
                    modifier = Modifier.padding(all = MarginSingle),
                )
                Spacer(modifier = Modifier.height(MarginSingle))
                ForecastWeather(
                    state = RainyHourForecast,
                    modifier = Modifier.padding(all = MarginSingle),
                )
            }
        }
    }
}
