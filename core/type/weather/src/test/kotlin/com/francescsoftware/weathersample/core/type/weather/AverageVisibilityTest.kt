package com.francescsoftware.weathersample.core.type.weather

import assertk.assertions.isTrue
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

internal class AverageVisibilityTest {

    @ParameterizedTest
    @ArgumentsSource(AverageVisibilityArgumentProvider::class)
    fun `kilometers constructor returns correct values`(
        kilometers: Double,
        miles: Double,
    ) {
        val averageVisibility = AverageVisibility.fromKm(kilometers)

        assertk.assertThat(compare(averageVisibility.kilometers, kilometers)).isTrue()
        assertk.assertThat(compare(averageVisibility.miles, miles)).isTrue()
    }

    @ParameterizedTest
    @ArgumentsSource(AverageVisibilityArgumentProvider::class)
    fun `miles constructor returns correct values`(
        kilometers: Double,
        miles: Double,
    ) {
        val averageVisibility = AverageVisibility.fromMiles(miles)

        assertk.assertThat(compare(averageVisibility.kilometers, kilometers)).isTrue()
        assertk.assertThat(compare(averageVisibility.miles, miles)).isTrue()
    }
}

private class AverageVisibilityArgumentProvider : ArgumentsProvider {

    class VisibilityPairs(
        val kilometers: Double,
        val miles: Double,
    )

    private val pairs = listOf(
        VisibilityPairs(
            kilometers = 2.5,
            miles = 1.558603491,
        ),
        VisibilityPairs(
            kilometers = 6.0,
            miles = 3.740648379,
        ),
        VisibilityPairs(
            miles = 4.0,
            kilometers = 6.416,
        ),
        VisibilityPairs(
            miles = 8.5,
            kilometers = 13.634,
        ),
    )

    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> =
        pairs.map { Arguments.of(it.kilometers, it.miles) }.stream()
}
