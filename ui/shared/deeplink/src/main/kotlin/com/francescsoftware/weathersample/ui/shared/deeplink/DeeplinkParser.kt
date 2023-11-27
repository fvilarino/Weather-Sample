package com.francescsoftware.weathersample.ui.shared.deeplink

import android.content.Intent
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Deeplink wrapper
 *
 * @property screens list of [Screen]s to navigate to.
 */
class DeeplinkPayload(
    private val screens: List<Screen>,
) {
    private val consumed = AtomicBoolean(false)

    /**
     * Indicates whether this payload has already been consumed.
     */
    val isConsumed: Boolean
        get() = consumed.get()

    /**
     * Consumes the payload if not yet consumed.
     *
     * @return a list of [Screen]s to navigate to if not consumed, null otherwise.
     */
    fun consume(): List<Screen>? = if (!consumed.getAndSet(true)) {
        screens
    } else {
        null
    }
}

/** Parses deeplinks */
interface DeeplinkParser {

    /** A [Flow] broadcasting a [DeeplinkPayload] parsed from a deeplink. */
    val events: Flow<DeeplinkPayload>

    /**
     * Parses an [Intent] to extract the deeplink information.
     *
     * @param intent the deeplink [Intent]
     */
    fun parse(intent: Intent)
}
