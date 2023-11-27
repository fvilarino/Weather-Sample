package com.francescsoftware.weathersample.ui.shared.deeplink

import com.slack.circuit.runtime.screen.Screen

interface LinkableDestination {
    fun parse(segments: List<String>): List<Screen>?
}
