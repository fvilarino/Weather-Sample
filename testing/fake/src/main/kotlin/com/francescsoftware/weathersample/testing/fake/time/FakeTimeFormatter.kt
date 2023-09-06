package com.francescsoftware.weathersample.testing.fake.time

import com.francescsoftware.weathersample.core.time.api.TimeFormatter
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/** An implementation of [TimeFormatter] to be used in tests */
class FakeTimeFormatter : TimeFormatter {

    /** @{inheritDoc} */
    override fun formatDayWithDayOfWeek(zonedDateTime: ZonedDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("EEE MMMM, dd")
        return formatter.format(zonedDateTime)
    }

    /** @{inheritDoc} */
    override fun formatHour(zonedDateTime: ZonedDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return formatter.format(zonedDateTime)
    }
}
