package com.francescsoftware.weathersample.time.impl

import com.francescsoftware.weathersample.time.api.Iso8601Date
import com.francescsoftware.weathersample.time.api.Iso8601DateTime
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.Calendar

internal class TimeParserTest {

    private val parser = TimeParserImpl()

    @Test
    fun `Iso8601Date gets converted to Date`() {
        val year = "2022"
        val month = "02"
        val day = "25"
        val date = "$year-$month-$day"
        val isoDate = Iso8601Date(date)
        val parsed = parser.parseDate(isoDate)
        val calendar = Calendar.getInstance().apply { time = parsed }
        assertAll(
            "date",
            {
                assertEquals(
                    calendar.get(Calendar.YEAR),
                    year.toInt(),
                )
            },
            {
                assertEquals(
                    calendar.get(Calendar.MONTH) + 1,
                    month.toInt(),
                )
            },
            {
                assertEquals(
                    calendar.get(Calendar.DAY_OF_MONTH),
                    day.toInt(),
                )
            },
        )
    }

    @Test
    fun `Iso8601DateTime gets converted to Date`() {
        val year = "2022"
        val month = "02"
        val day = "25"
        val hour = "18"
        val minute = "36"
        val date = "$year-$month-$day $hour:$minute"
        val isoDate = Iso8601DateTime(date)
        val parsed = parser.parseDate(isoDate)
        val calendar = Calendar.getInstance().apply { time = parsed }
        assertAll(
            "date",
            {
                assertEquals(
                    calendar.get(Calendar.YEAR),
                    year.toInt(),
                )
            },
            {
                assertEquals(
                    calendar.get(Calendar.MONTH) + 1,
                    month.toInt(),
                )
            },
            {
                assertEquals(
                    calendar.get(Calendar.DAY_OF_MONTH),
                    day.toInt(),
                )
            },
            {
                assertEquals(
                    calendar.get(Calendar.HOUR_OF_DAY),
                    hour.toInt(),
                )
            },
            {
                assertEquals(
                    calendar.get(Calendar.MINUTE),
                    minute.toInt(),
                )
            },
        )
    }
}
