package com.francescsoftware.weathersample.presentation.feature.navigator

import androidx.lifecycle.DefaultLifecycleObserver
import com.francescsoftware.weathersample.presentation.feature.weather.SelectedCity

interface Navigator : DefaultLifecycleObserver {
    fun cityToWeather(city: SelectedCity)
    fun goBack()
}
