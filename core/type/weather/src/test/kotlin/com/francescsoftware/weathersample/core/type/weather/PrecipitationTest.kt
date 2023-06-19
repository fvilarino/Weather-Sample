package com.francescsoftware.weathersample.core.type.weather

import assertk.assertions.isTrue
import org.junit.jupiter.api.Test

private const val InchesToMillimeters = 25.4

internal class PrecipitationTest {

    @Test
    fun `millimeters constructor returns correct values`() {
        val millimeters = 12.3
        val precipitation = Precipitation.fromMillimeters(millimeters)

        assertk.assertThat(compare(precipitation.millimeters, millimeters)).isTrue()
        assertk.assertThat(compare(precipitation.inches, millimeters / InchesToMillimeters)).isTrue()
    }

    @Test
    fun `inches constructor returns correct values`() {
        val inches = 2.35
        val precipitation = Precipitation.fromInches(inches)

        assertk.assertThat(compare(precipitation.millimeters, inches * InchesToMillimeters)).isTrue()
        assertk.assertThat(compare(precipitation.inches, inches)).isTrue()
    }
}
