package com.francescsoftware.weathersample.core.type.weather

import assertk.assertions.isTrue
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

internal class PressureTest {

    @ParameterizedTest
    @ArgumentsSource(PressureArgumentProvider::class)
    fun `millibars constructor returns correct values`(
        millibars: Double,
        hgInches: Double,
    ) {
        val pressure = Pressure.fromMillibars(millibars)

        assertk.assertThat(compare(pressure.millibars, millibars)).isTrue()
        assertk.assertThat(compare(pressure.hgInches, hgInches)).isTrue()
    }

    @ParameterizedTest
    @ArgumentsSource(PressureArgumentProvider::class)
    fun `hg inches constructor returns correct values`(
        millibars: Double,
        hgInches: Double,
    ) {
        val pressure = Pressure.fromHgInches(hgInches)

        assertk.assertThat(compare(pressure.millibars, millibars)).isTrue()
        assertk.assertThat(compare(pressure.hgInches, hgInches)).isTrue()
    }
}

private class PressureArgumentProvider : ArgumentsProvider {

    class PressurePairs(
        val millibars: Double,
        val hgInches: Double,
    )

    private val pairs = listOf(
        PressurePairs(
            millibars = 1024.0,
            hgInches = 30.238601465,
        ),
        PressurePairs(
            millibars = 998.5,
            hgInches = 29.485589416,
        ),
        PressurePairs(
            hgInches = 30.25,
            millibars = 1024.386,
        ),
        PressurePairs(
            hgInches = 30.02,
            millibars = 1016.59728,
        ),
    )

    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> =
        pairs.map { Arguments.of(it.millibars, it.hgInches) }.stream()
}
