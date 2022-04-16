package com.francescsoftware.weathersample.feature.weather.viewmodel

import com.francescsoftware.weathersample.feature.weather.SelectedWeatherScreen

internal interface WeatherCallbacks {
    fun onOptionSelect(selectedWeatherScreen: SelectedWeatherScreen)
    fun refreshTodayWeather()
    fun retry()
}
