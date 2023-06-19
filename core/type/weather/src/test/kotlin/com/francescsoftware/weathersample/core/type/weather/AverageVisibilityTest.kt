package com.francescsoftware.weathersample.core.type.weather

import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import org.junit.jupiter.api.Test

private const val MilesToKilometers = 1.604

internal class AverageVisibilityTest {

    @Test
    fun `kilometers constructor returns correct values`() {
        val kilometers = 3.75
        val averageVisibility = AverageVisibility.fromKm(kilometers)

        assertk.assertThat(compare(averageVisibility.kilometers, kilometers)).isTrue()
        assertk.assertThat(compare(averageVisibility.miles, kilometers / MilesToKilometers)).isTrue()
    }

    @Test
    fun `miles constructor returns correct values`() {
        val miles = 2.35
        val averageVisibility = AverageVisibility.fromMiles(miles)

        assertk.assertThat(compare(averageVisibility.kilometers, miles * MilesToKilometers)).isTrue()
        assertk.assertThat(compare(averageVisibility.miles, miles)).isTrue()
    }
}
