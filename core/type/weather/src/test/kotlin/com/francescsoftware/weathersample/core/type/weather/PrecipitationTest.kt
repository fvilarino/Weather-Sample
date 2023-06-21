package com.francescsoftware.weathersample.core.type.weather

import assertk.assertions.isTrue
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

internal class PrecipitationTest {

    @ParameterizedTest
    @ArgumentsSource(PrecipitationArgumentProvider::class)
    fun `millimeters constructor returns correct values`(
        millimeters: Double,
        inches: Double,
    ) {
        val precipitation = Precipitation.fromMillimeters(millimeters)

        assertk.assertThat(compare(precipitation.millimeters, millimeters)).isTrue()
        assertk.assertThat(compare(precipitation.inches, inches)).isTrue()
    }

    @ParameterizedTest
    @ArgumentsSource(PrecipitationArgumentProvider::class)
    fun `inches constructor returns correct values`(
        millimeters: Double,
        inches: Double,
    ) {
        val precipitation = Precipitation.fromInches(inches)

        assertk.assertThat(compare(precipitation.millimeters, millimeters)).isTrue()
        assertk.assertThat(compare(precipitation.inches, inches)).isTrue()
    }
}

private class PrecipitationArgumentProvider : ArgumentsProvider {

    class PrecipitationPairs(
        val millimeters: Double,
        val inches: Double,
    )

    private val pairs = listOf(
        PrecipitationPairs(
            millimeters = 2.5,
            inches = 0.098425197,
        ),
        PrecipitationPairs(
            millimeters = 16.5,
            inches = 0.649606299,
        ),
        PrecipitationPairs(
            inches = 1.5,
            millimeters = 38.1,
        ),
        PrecipitationPairs(
            inches = .65,
            millimeters = 16.51,
        ),
    )

    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> =
        pairs.map { Arguments.of(it.millimeters, it.inches) }.stream()
}
