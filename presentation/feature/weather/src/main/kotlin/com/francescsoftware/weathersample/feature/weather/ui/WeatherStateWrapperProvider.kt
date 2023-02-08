package com.francescsoftware.weathersample.feature.weather.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.francescsoftware.weathersample.feature.weather.viewmodel.ForecastDayState
import com.francescsoftware.weathersample.feature.weather.viewmodel.ForecastHeaderState
import com.francescsoftware.weathersample.feature.weather.viewmodel.TodayWeatherCardState
import com.francescsoftware.weathersample.feature.weather.viewmodel.WeatherLoadState
import com.francescsoftware.weathersample.feature.weather.viewmodel.WeatherState
import com.francescsoftware.weathersample.shared.assets.R

internal data class WeatherStateWrapper(
    val state: WeatherState,
    val option: SelectedWeatherOption,
)

internal class WeatherStateWrapperProvider : PreviewParameterProvider<WeatherStateWrapper> {
    override val values: Sequence<WeatherStateWrapper> = sequenceOf(
        WeatherStateWrapper(state = todayState, option = SelectedWeatherOption.Today),
        WeatherStateWrapper(state = todayState, option = SelectedWeatherOption.Forecast),
        WeatherStateWrapper(
            state = todayState.copy(
                loadState = WeatherLoadState.Error,
                errorMessage = "Failed to load weather data",
            ),
            option = SelectedWeatherOption.Today
        ),
    )
}

private val todayState = WeatherState(
    loadState = WeatherLoadState.Loaded,
    cityName = "Coquitlam, British Columbia",
    cityCountryCode = "CA",
    todayState = TodayWeatherCardState(
        temperature = "16.4°C",
        feelsLikeTemperature = "14.3°C",
        precipitation = "10",
        uvIndex = "5",
        description = "Partly cloudy",
        iconId = R.drawable.ic_partly_cloudy,
        windSpeed = "4.3kph",
        humidity = "54%",
        pressure = "1024mb",
        visibility = "10 km",
    ),
    forecastItems = listOf(
        ForecastDayState(
            header = ForecastHeaderState(
                id = "header",
                date = "Aug 18, 2022",
                sunrise = "5:44am",
                sunset = "8:51pm",
            ),
            forecast = listOf(
                PartlyCloudyHourForecast,
                SunnyHourForecast,
                HeavyRainHourForecast,
                SnowHourForecast,
                LightRainHourForecast,
            ),
        )
    ),
    errorMessage = "",
)
