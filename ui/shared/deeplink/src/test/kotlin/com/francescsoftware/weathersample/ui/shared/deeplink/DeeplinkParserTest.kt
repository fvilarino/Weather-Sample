package com.francescsoftware.weathersample.ui.shared.deeplink

import android.content.Intent
import android.net.Uri
import android.os.Parcel
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNull
import com.slack.circuit.runtime.screen.Screen
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

private object FakeHomeScreen : Screen {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
    }
}

private data class FakeWeatherScreen(
    val city: String,
    val countryCode: String,
) : Screen {

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
    }

    companion object Parser : LinkableDestination {
        override fun parse(segments: List<String>): List<Screen> {
            return listOf(
                FakeHomeScreen,
                FakeWeatherScreen(
                    city = segments[0],
                    countryCode = segments[1],
                ),
            )
        }
    }
}

private object FakeFavoriteScreen : Screen, LinkableDestination {
    override fun parse(segments: List<String>): List<Screen> {
        return listOf(FakeFavoriteScreen)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
    }
}

private const val FavoriteHost = "favorite"
private const val WeatherHost = "weather"

internal class DeeplinkParserTest {

    private val destinations: Map<String, LinkableDestination> = mapOf(
        WeatherHost to FakeWeatherScreen,
        FavoriteHost to FakeFavoriteScreen,
    )

    @Test
    @DisplayName("Deeplink parser ignores incorrect intent action")
    fun parser_ignores_incorrect_action() = runTest {
        val intent = mockk<Intent>()
        every { intent.action } returns Intent.ACTION_CALL
        val parser = DeeplinkParserImpl(destinations)
        parser.parse(intent)
        parser.events.test {
            expectNoEvents()
        }
    }

    @Test
    @DisplayName("Deeplink parser handles favorite deeplink")
    fun parser_handles_favorite_deeplink() = runTest {
        val intent = mockk<Intent>()
        val uri = mockk<Uri>()
        every { intent.action } returns Intent.ACTION_VIEW
        every { intent.scheme } returns DeeplinkScheme
        every { intent.data } returns uri
        every { uri.host } returns FavoriteHost
        every { uri.pathSegments } returns emptyList<String>()
        val parser = DeeplinkParserImpl(destinations)
        parser.parse(intent)
        parser.events.test {
            val payload = awaitItem()
            assertThat(payload.isConsumed).isFalse()
            assertThat(payload.consume()).isEqualTo(listOf(FakeFavoriteScreen))
        }
    }

    @Test
    @DisplayName("Deeplink parser handles weather deeplink")
    fun parser_handles_weather_deeplink() = runTest {
        val intent = mockk<Intent>()
        val uri = mockk<Uri>()
        val city = "vancouver"
        val countryCode = "ca"
        every { intent.action } returns Intent.ACTION_VIEW
        every { intent.scheme } returns DeeplinkScheme
        every { intent.data } returns uri
        every { uri.host } returns WeatherHost
        every { uri.pathSegments } returns listOf(city, countryCode)
        val parser = DeeplinkParserImpl(destinations)
        parser.parse(intent)
        parser.events.test {
            val payload = awaitItem()
            assertThat(payload.isConsumed).isFalse()
            assertThat(payload.consume()).isEqualTo(
                listOf(
                    FakeHomeScreen,
                    FakeWeatherScreen(
                        city = city,
                        countryCode = countryCode,
                    ),
                ),
            )
        }
    }

    @Test
    @DisplayName("Deeplink parser ignores unknown schema")
    fun parser_ignores_unknown_schema() = runTest {
        val intent = mockk<Intent>()
        val uri = mockk<Uri>()
        every { intent.action } returns Intent.ACTION_VIEW
        every { intent.scheme } returns "UnknownSchema"
        every { intent.data } returns uri
        every { uri.host } returns FavoriteHost
        every { uri.pathSegments } returns emptyList()
        val parser = DeeplinkParserImpl(destinations)
        parser.parse(intent)
        parser.events.test {
            expectNoEvents()
        }
    }

    @Test
    @DisplayName("Deeplink parser ignores unknown hosts")
    fun parser_ignores_unknown_hosts() = runTest {
        val intent = mockk<Intent>()
        val uri = mockk<Uri>()
        every { intent.action } returns Intent.ACTION_VIEW
        every { intent.scheme } returns DeeplinkScheme
        every { intent.data } returns uri
        every { uri.host } returns "UnknownHost"
        every { uri.pathSegments } returns emptyList()
        val parser = DeeplinkParserImpl(destinations)
        parser.parse(intent)
        parser.events.test {
            expectNoEvents()
        }
    }

    @Test
    @DisplayName("Deeplinks are consumed only once")
    fun deeplinks_consumed_only_once() = runTest {
        val intent = mockk<Intent>()
        val uri = mockk<Uri>()
        every { intent.action } returns Intent.ACTION_VIEW
        every { intent.scheme } returns DeeplinkScheme
        every { intent.data } returns uri
        every { uri.host } returns FavoriteHost
        every { uri.pathSegments } returns emptyList<String>()
        val parser = DeeplinkParserImpl(destinations)
        parser.parse(intent)
        parser.events.test {
            val payload = awaitItem()
            payload.consume()
            val screens = payload.consume()
            assertThat(screens).isNull()
        }
    }
}
