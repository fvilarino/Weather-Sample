@file: Suppress("MagicNumber")

package com.francescsoftware.weathersample.ui.feature.search.weather.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.francescsoftware.weathersample.core.type.weather.AverageVisibility
import com.francescsoftware.weathersample.core.type.weather.Humidity
import com.francescsoftware.weathersample.core.type.weather.Precipitation
import com.francescsoftware.weathersample.core.type.weather.Pressure
import com.francescsoftware.weathersample.core.type.weather.Speed
import com.francescsoftware.weathersample.core.type.weather.Temperature
import com.francescsoftware.weathersample.core.type.weather.UvIndex
import com.francescsoftware.weathersample.ui.feature.search.weather.presenter.WeatherScreen
import com.francescsoftware.weathersample.ui.shared.composable.weather.CurrentWeatherState
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastDate
import com.francescsoftware.weathersample.ui.shared.composable.weather.ForecastHeaderState
import kotlinx.collections.immutable.persistentListOf
import com.francescsoftware.weathersample.ui.shared.assets.R as assetsR

internal data class WeatherStateWrapper(
    val state: WeatherScreen.State,
    val option: SelectedWeatherOption,
)

internal class WeatherStateWrapperProvider : PreviewParameterProvider<WeatherStateWrapper> {
    override val values: Sequence<WeatherStateWrapper> = sequenceOf(
        WeatherStateWrapper(state = todayState, option = SelectedWeatherOption.Today),
        WeatherStateWrapper(state = todayState, option = SelectedWeatherOption.Forecast),
    )
}

private val todayState = WeatherScreen.State(
    weather = WeatherScreen.Weather.Loaded(
        cityName = "Coquitlam, British Columbia",
        cityCountryCode = "CA",
        current = CurrentWeatherState(
            temperature = Temperature.fromCelsius(16.4),
            feelsLikeTemperature = Temperature.fromCelsius(14.3),
            precipitation = Precipitation.fromMillimeters(10.0),
            uvIndex = UvIndex(5),
            description = "Partly cloudy",
            iconId = assetsR.drawable.ic_partly_cloudy,
            windSpeed = Speed.fromKph(5.8),
            gustSpeed = Speed.fromKph(7.5),
            humidity = Humidity(54),
            pressure = Pressure.fromMillibars(1024.0),
            visibility = AverageVisibility.fromKm(7.3),
        ),
        forecast = persistentListOf(
            WeatherScreen.ForecastDayState(
                header = ForecastHeaderState(
                    id = "header",
                    date = ForecastDate.Day("Aug 18, 2022"),
                    sunrise = "5:44am",
                    sunset = "8:51pm",
                ),
                forecast = persistentListOf(
                    PartlyCloudyHourForecast,
                    SunnyHourForecast,
                    HeavyRainHourForecast,
                    SnowHourForecast,
                    LightRainHourForecast,
                ),
            ),
        ),
    ),
    refreshing = false,
    eventSink = {},
)
