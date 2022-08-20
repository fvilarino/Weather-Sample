package com.francescsoftware.weathersample.feature.weather

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class WeatherStateProvider : PreviewParameterProvider<WeatherState> {
    override val values: Sequence<WeatherState> = sequenceOf(
        todayState,
        forecastState,
        todayState.copy(
            loadState = WeatherLoadState.Error,
            errorMessage = "Failed to load weather data",
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
            ),
        )
    ),
    option = SelectedWeatherScreen.Today,
    errorMessage = "",
)

private val forecastState = todayState.copy(
    option = SelectedWeatherScreen.Forecast,
)
