package com.francescsoftware.weathersample.core.type.weather

import assertk.assertions.isTrue
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

internal class TemperatureTest {

    @ParameterizedTest
    @ArgumentsSource(TemperatureArgumentProvider::class)
    fun `celsius constructor returns correct values`(
        celsius: Double,
        fahrenheit: Double,
    ) {
        val temperature = Temperature.fromCelsius(celsius)

        assertk.assertThat(compare(temperature.celsius, celsius)).isTrue()
        assertk.assertThat(compare(temperature.fahrenheit, fahrenheit)).isTrue()
    }

    @ParameterizedTest
    @ArgumentsSource(TemperatureArgumentProvider::class)
    fun `fahrenheit constructor returns correct values`(
        celsius: Double,
        fahrenheit: Double,
    ) {
        val temperature = Temperature.fromFahrenheit(fahrenheit)

        assertk.assertThat(compare(temperature.celsius, celsius)).isTrue()
        assertk.assertThat(compare(temperature.fahrenheit, fahrenheit)).isTrue()
    }
}

private class TemperatureArgumentProvider : ArgumentsProvider {

    class TemperaturePairs(
        val celsius: Double,
        val fahrenheit: Double,
    )

    private val pairs = listOf(
        TemperaturePairs(
            celsius = 12.7,
            fahrenheit = 54.86,
        ),
        TemperaturePairs(
            celsius = -6.5,
            fahrenheit = 20.3,
        ),
        TemperaturePairs(
            fahrenheit = 94.3,
            celsius = 34.61111,
        ),
        TemperaturePairs(
            fahrenheit = 67.8,
            celsius = 19.88889,
        ),
    )

    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> =
        pairs.map { Arguments.of(it.celsius, it.fahrenheit) }.stream()
}
