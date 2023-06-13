package com.francescsoftware.weathersample.core.time.api

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import org.junit.jupiter.api.Test

internal class IsoDateTest {

    @Test
    fun `valid date is parsed`() {
        val date = "2023-02-25"
        val isoDate = Iso8601Date(date)
        assertThat(isoDate.date).isEqualTo(date)
    }

    @Test
    fun `valid datetime is parsed`() {
        val date = "2023-02-25 16:34"
        val isoDate = Iso8601DateTime(date)
        assertThat(isoDate.date).isEqualTo(date)
    }

    @Test
    fun `invalid date throws exception`() {
        val date = "2023-02"
        assertFailure {
            Iso8601Date(date)
        }.isInstanceOf<TimeParsingException>()
    }

    @Test
    fun `invalid datetime throws exception`() {
        val date = "2023-02-25 14-21"
        assertFailure {
            Iso8601DateTime(date)
        }.isInstanceOf<TimeParsingException>()
    }
}
