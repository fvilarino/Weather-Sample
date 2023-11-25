package com.francescsoftware.weathersample.ui.feature.home.deeplink

import android.content.Intent
import kotlinx.coroutines.flow.Flow

sealed interface DeeplinkEvent {
    data object Favorites : DeeplinkEvent
    data class Weather(val city: String, val countryCode: String) : DeeplinkEvent
}

interface DeeplinkParser {
    val events: Flow<DeeplinkEvent?>
    fun parse(intent: Intent)
    fun deeplinkConsumed()
}
