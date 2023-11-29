package com.francescsoftware.weathersample.ui.shared.deeplink

import android.content.Intent
import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

internal const val DeeplinkScheme = "weatherapp"

@ContributesBinding(ActivityScope::class)
@SingleIn(ActivityScope::class)
class DeeplinkParserImpl @Inject constructor(
    private val parsers: @JvmSuppressWildcards Map<String, LinkableDestination>,
) : DeeplinkParser {

    private val _events = MutableSharedFlow<DeeplinkPayload>(
        replay = 1,
    )

    override val events: Flow<DeeplinkPayload>
        get() = _events

    override fun parse(intent: Intent) {
        if (intent.action == Intent.ACTION_VIEW) {
            val uri = intent.data ?: return
            val host = uri.host
            val segments = uri.pathSegments.orEmpty()
            val queryParamNames = uri.queryParameterNames.orEmpty()
            val params = queryParamNames.mapNotNull { key ->
                uri.getQueryParameter(key)?.let { value -> key to value }
            }.toMap()
            if (DeeplinkScheme == intent.scheme && host != null) {
                parsers[host]?.parse(segments, params)?.let { screens ->
                    _events.tryEmit(
                        DeeplinkPayload(
                            screens,
                        ),
                    )
                }
            }
        }
    }
}
