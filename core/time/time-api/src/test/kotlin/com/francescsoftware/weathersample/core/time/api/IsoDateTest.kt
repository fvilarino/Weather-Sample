package com.francescsoftware.weathersample.core.time.api

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import org.junit.jupiter.api.Test

internal class IsoDateTest {

    @Test
    fun `valid datetime is parsed`() {
        val date = "2023-02-25 16:34"
        val isoDate = Iso8601DateTime(date)
        assertThat(isoDate.date).isEqualTo(date)
    }

    @Test
    fun `invalid datetime throws exception`() {
        val date1 = "2023-02-25 14-21"
        assertFailure {
            Iso8601DateTime(date1)
        }.isInstanceOf<TimeParsingException>()
        val date2 = "2023-02-25 14:2"
        assertFailure {
            Iso8601DateTime(date2)
        }.isInstanceOf<TimeParsingException>()
    }

    @Test
    fun `invalid too low month throws exception`() {
        val date = "2023-00-25 14:21"
        assertFailure {
            Iso8601DateTime(date)
        }.isInstanceOf<TimeParsingException>()
    }

    @Test
    fun `invalid too high month throws exception`() {
        val date = "2023-15-25 14:21"
        assertFailure {
            Iso8601DateTime(date)
        }.isInstanceOf<TimeParsingException>()
    }

    @Test
    fun `invalid too low day throws exception`() {
        val date = "2023-03-00 14:21"
        assertFailure {
            Iso8601DateTime(date)
        }.isInstanceOf<TimeParsingException>()
    }

    @Test
    fun `invalid too high day throws exception`() {
        val date = "2023-03-35 14:21"
        assertFailure {
            Iso8601DateTime(date)
        }.isInstanceOf<TimeParsingException>()
    }

    @Test
    fun `invalid hour throws exception`() {
        val date = "2023-03-22 26:21"
        assertFailure {
            Iso8601DateTime(date)
        }.isInstanceOf<TimeParsingException>()
    }

    @Test
    fun `invalid minute throws exception`() {
        val date = "2023-03-22 16:71"
        assertFailure {
            Iso8601DateTime(date)
        }.isInstanceOf<TimeParsingException>()
    }
}
