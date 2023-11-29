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
import io.mockk.slot
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
        override fun parse(
            segments: List<String>,
            queryParams: Map<String, String>,
        ): List<Screen> {
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
    override fun parse(
        segments: List<String>,
        queryParams: Map<String, String>,
    ): List<Screen> {
        return listOf(FakeFavoriteScreen)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
    }
}

private data class FakeSettingsScreen(
    val param1: String,
    val param2: String,
) : Screen {

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
    }

    companion object Parser : LinkableDestination {
        const val QueryParam1 = "param1"
        const val QueryParam2 = "param2"

        override fun parse(
            segments: List<String>,
            queryParams: Map<String, String>,
        ): List<Screen> {
            return listOf(
                FakeSettingsScreen(
                    param1 = requireNotNull(queryParams[QueryParam1]),
                    param2 = requireNotNull(queryParams[QueryParam2]),
                ),
            )
        }
    }
}

private const val FavoriteHost = "favorite"
private const val WeatherHost = "weather"
private const val SettingsHost = "settings"

internal class DeeplinkParserTest {

    private val destinations: Map<String, LinkableDestination> = mapOf(
        WeatherHost to FakeWeatherScreen,
        FavoriteHost to FakeFavoriteScreen,
        SettingsHost to FakeSettingsScreen,
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
    @DisplayName("Deeplink parser handles host deeplink")
    fun parser_handles_host_deeplink() = runTest {
        val intent = mockk<Intent>()
        val uri = mockk<Uri>()
        every { intent.action } returns Intent.ACTION_VIEW
        every { intent.scheme } returns DeeplinkScheme
        every { intent.data } returns uri
        every { uri.host } returns FavoriteHost
        every { uri.pathSegments } returns emptyList<String>()
        every { uri.queryParameterNames } returns emptySet<String>()
        val parser = DeeplinkParserImpl(destinations)
        parser.parse(intent)
        parser.events.test {
            val payload = awaitItem()
            assertThat(payload.isConsumed).isFalse()
            assertThat(payload.consume()).isEqualTo(listOf(FakeFavoriteScreen))
        }
    }

    @Test
    @DisplayName("Deeplink parser handles deeplink with path")
    fun parser_handles_deeplink_path() = runTest {
        val intent = mockk<Intent>()
        val uri = mockk<Uri>()
        val city = "vancouver"
        val countryCode = "ca"
        every { intent.action } returns Intent.ACTION_VIEW
        every { intent.scheme } returns DeeplinkScheme
        every { intent.data } returns uri
        every { uri.host } returns WeatherHost
        every { uri.pathSegments } returns listOf(city, countryCode)
        every { uri.queryParameterNames } returns emptySet<String>()
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
    @DisplayName("Deeplink parser handles deeplink query parameters")
    fun parser_handles_deeplink_query_parameters() = runTest {
        val intent = mockk<Intent>()
        val uri = mockk<Uri>()
        val param1 = "vancouver"
        val param2 = "barcelona"
        every { intent.action } returns Intent.ACTION_VIEW
        every { intent.scheme } returns DeeplinkScheme
        every { intent.data } returns uri
        every { uri.host } returns SettingsHost
        every { uri.pathSegments } returns emptyList<String>()
        every { uri.queryParameterNames } returns setOf(FakeSettingsScreen.QueryParam1, FakeSettingsScreen.QueryParam2)
        val querySlot = slot<String>()
        every { uri.getQueryParameter(capture(querySlot)) } answers {
            when (querySlot.captured) {
                FakeSettingsScreen.QueryParam1 -> param1
                FakeSettingsScreen.QueryParam2 -> param2
                else -> error("Invalid query parameter")
            }
        }
        val parser = DeeplinkParserImpl(destinations)
        parser.parse(intent)
        parser.events.test {
            val payload = awaitItem()
            assertThat(payload.isConsumed).isFalse()
            assertThat(payload.consume()).isEqualTo(
                listOf(
                    FakeSettingsScreen(
                        param1 = param1,
                        param2 = param2,
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
        every { uri.queryParameterNames } returns emptySet<String>()
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
        every { uri.queryParameterNames } returns emptySet<String>()
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
        every { uri.queryParameterNames } returns emptySet<String>()
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
