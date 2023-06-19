package com.francescsoftware.weathersample.core.type.weather

import assertk.assertions.isTrue
import org.junit.jupiter.api.Test

internal class TemperatureTest {

    @Test
    fun `celsius constructor returns correct values`() {
        val celsius = 21.7
        val temperature = Temperature.fromCelsius(celsius)

        assertk.assertThat(compare(temperature.celsius, celsius)).isTrue()
        assertk.assertThat(compare(temperature.fahrenheit, celsiusToFahrenheit(celsius))).isTrue()
    }

    @Test
    fun `fahrenheit constructor returns correct values`() {
        val fahrenheit = 2.35
        val temperature = Temperature.fromFahrenheit(fahrenheit)

        assertk.assertThat(compare(temperature.celsius, fahrenheitToCelsius(fahrenheit))).isTrue()
        assertk.assertThat(compare(temperature.fahrenheit, fahrenheit)).isTrue()
    }

    private fun celsiusToFahrenheit(celsius: Double): Double = (celsius * 9.0 / 5.0) + 32.0
    private fun fahrenheitToCelsius(fahrenheit: Double): Double = (fahrenheit - 32.0) * 5.0 / 9.0
}
