package com.francescsoftware.weathersample.time.api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

internal class IsoDateTest {

    @Test
    fun `valid date is parsed`() {
        val date = "2023-02-25"
        val isoDate = Iso8601Date(date)
        assertEquals(
            date,
            isoDate.date,
        )
    }

    @Test
    fun `valid datetime is parsed`() {
        val date = "2023-02-25 16:34"
        val isoDate = Iso8601DateTime(date)
        assertEquals(
            date,
            isoDate.date,
        )
    }

    @Test
    fun `invalid date throws exception`() {
        val date = "2023-02"
        assertFailsWith(TimeParsingException::class) {
            Iso8601Date(date)
        }
    }

    @Test
    fun `invalid datetime throws exception`() {
        val date = "2023-02-25 14-21"
        assertFailsWith(TimeParsingException::class) {
            Iso8601DateTime(date)
        }
    }
}
