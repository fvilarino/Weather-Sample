package com.francescsoftware.weathersample.core.type.weather

import assertk.assertions.isTrue
import org.junit.jupiter.api.Test

private const val MilesToKilometers = 1.604

internal class SpeedTest {

    @Test
    fun `kilometers per hour constructor returns correct values`() {
        val kph = 12.5
        val speed = Speed.fromKph(kph)

        assertk.assertThat(compare(speed.kph, kph)).isTrue()
        assertk.assertThat(compare(speed.mph, kph / MilesToKilometers)).isTrue()
    }

    @Test
    fun `miles per hour constructor returns correct values`() {
        val mph = 3.75
        val speed = Speed.fromMph(mph)

        assertk.assertThat(compare(speed.kph, mph * MilesToKilometers)).isTrue()
        assertk.assertThat(compare(speed.mph, mph)).isTrue()
    }
}
