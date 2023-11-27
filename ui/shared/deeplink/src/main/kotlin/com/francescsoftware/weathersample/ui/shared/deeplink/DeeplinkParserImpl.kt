package com.francescsoftware.weathersample.ui.shared.deeplink

import android.content.Intent
import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.slack.circuit.runtime.screen.Screen
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

private const val DeeplinkScheme = "weatherapp"

@ContributesBinding(ActivityScope::class)
@SingleIn(ActivityScope::class)
class DeeplinkParserImpl @Inject constructor(
    private val parsers: @JvmSuppressWildcards Map<String, LinkableDestination>,
) : DeeplinkParser {

    private val _events = MutableSharedFlow<List<Screen>?>(
        replay = 1,
    )

    override val events: Flow<List<Screen>?>
        get() = _events

    override fun parse(intent: Intent) {
        if (intent.action == Intent.ACTION_VIEW) {
            val uri = intent.data
            val host = uri?.host
            val segments = uri?.pathSegments.orEmpty()
            if (DeeplinkScheme == intent.scheme && host != null) {
                parsers[host]?.parse(segments)?.let { screens ->
                    _events.tryEmit(screens)
                }
            }
        }
    }

    override fun deeplinkConsumed() {
        _events.tryEmit(null)
    }
}
