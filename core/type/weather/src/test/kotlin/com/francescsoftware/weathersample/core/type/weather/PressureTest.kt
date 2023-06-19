package com.francescsoftware.weathersample.core.type.weather

import assertk.assertions.isTrue
import org.junit.jupiter.api.Test

private const val MbToInchesFactor = 33.864

internal class PressureTest {

    @Test
    fun `millibars constructor returns correct values`() {
        val millibars = 1024.0
        val pressure = Pressure.fromMillibars(millibars)

        assertk.assertThat(compare(pressure.millibars, millibars)).isTrue()
        assertk.assertThat(compare(pressure.hgInches, millibars * MbToInchesFactor)).isTrue()
    }

    @Test
    fun `hg inches constructor returns correct values`() {
        val hgInches = 3.07
        val pressure = Pressure.fromHgInches(hgInches)

        assertk.assertThat(compare(pressure.millibars, hgInches / MbToInchesFactor)).isTrue()
        assertk.assertThat(compare(pressure.hgInches, hgInches)).isTrue()
    }
}
