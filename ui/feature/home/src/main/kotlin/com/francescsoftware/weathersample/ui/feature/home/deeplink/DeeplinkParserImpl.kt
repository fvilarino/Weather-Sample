package com.francescsoftware.weathersample.ui.feature.home.deeplink

import android.content.Intent
import android.net.Uri
import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

private const val DeeplinkScheme = "weatherapp"
private const val DeeplinkWeatherHost = "weather"
private const val DeeplinkFavoritesHost = "favorite"

@ContributesBinding(ActivityScope::class)
@SingleIn(ActivityScope::class)
class DeeplinkParserImpl @Inject constructor() : DeeplinkParser {

    private val _events = MutableSharedFlow<DeeplinkEvent?>(
        replay = 1,
    )

    override val events: Flow<DeeplinkEvent?>
        get() = _events

    override fun parse(intent: Intent) {
        val uri = intent.data
        if (DeeplinkScheme == intent.scheme && uri != null) {
            when (uri.host) {
                DeeplinkWeatherHost -> parseWeatherDeeplink(uri)
                DeeplinkFavoritesHost -> parseFavoritesDeeplink(uri)
            }
        }
    }

    override fun deeplinkConsumed() {
        _events.tryEmit(null)
    }

    private fun parseWeatherDeeplink(uri: Uri) {
        val segments = uri.pathSegments
        if (segments.size == 2) {
            _events.tryEmit(
                DeeplinkEvent.Weather(
                    city = segments[0],
                    countryCode = segments[1],
                ),
            )
        }
    }

    private fun parseFavoritesDeeplink(uri: Uri) {
        if (uri.path.isNullOrEmpty()) {
            _events.tryEmit(DeeplinkEvent.Favorites)
        }
    }
}
