package com.francescsoftware.weathersample.ui.shared.deeplink

import android.content.Intent
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.flow.Flow

interface DeeplinkParser {
    val events: Flow<List<Screen>?>
    fun parse(intent: Intent)
    fun deeplinkConsumed()
}
