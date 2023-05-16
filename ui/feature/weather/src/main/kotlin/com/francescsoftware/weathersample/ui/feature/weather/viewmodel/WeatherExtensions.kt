package com.francescsoftware.weathersample.ui.feature.weather.viewmodel

import com.francescsoftware.weathersample.domain.interactor.weather.api.model.TodayMain
import java.util.Locale

internal fun TodayMain.formatDescription(): String =
    description.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
