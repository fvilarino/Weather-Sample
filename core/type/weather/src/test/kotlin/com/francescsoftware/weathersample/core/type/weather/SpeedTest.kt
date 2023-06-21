package com.francescsoftware.weathersample.core.type.weather

import assertk.assertions.isTrue
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

internal class SpeedTest {

    @ParameterizedTest
    @ArgumentsSource(SpeedArgumentProvider::class)
    fun `kilometers per hour constructor returns correct values`(
        kph: Double,
        mph: Double,
    ) {
        val speed = Speed.fromKph(kph)

        assertk.assertThat(compare(speed.kph, kph)).isTrue()
        assertk.assertThat(compare(speed.mph, mph)).isTrue()
    }

    @ParameterizedTest
    @ArgumentsSource(SpeedArgumentProvider::class)
    fun `miles per hour constructor returns correct values`(
        kph: Double,
        mph: Double,
    ) {
        val speed = Speed.fromMph(mph)

        assertk.assertThat(compare(speed.kph, kph)).isTrue()
        assertk.assertThat(compare(speed.mph, mph)).isTrue()
    }
}

private class SpeedArgumentProvider : ArgumentsProvider {

    class SpeedPairs(
        val kph: Double,
        val mph: Double,
    )

    private val pairs = listOf(
        SpeedPairs(
            kph = 2.5,
            mph = 1.558603491,
        ),
        SpeedPairs(
            kph = 6.0,
            mph = 3.740648379,
        ),
        SpeedPairs(
            mph = 4.0,
            kph = 6.416,
        ),
        SpeedPairs(
            mph = 8.5,
            kph = 13.634,
        ),
    )

    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> =
        pairs.map { Arguments.of(it.kph, it.mph) }.stream()
}
