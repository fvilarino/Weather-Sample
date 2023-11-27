package com.francescsoftware.weathersample.ui.shared.deeplink

import com.slack.circuit.runtime.screen.Screen

/** Interface implemented by [Screen]s that are deeplink destinations. */
interface LinkableDestination {

    /**
     * Parses a list of segments from the deeplink.
     *
     * @param segments the deeplink payload from the Intent's URI
     * @return a list of screens to navigate to if the segments can be parsed, null otherwise
     */
    fun parse(segments: List<String>): List<Screen>?
}
