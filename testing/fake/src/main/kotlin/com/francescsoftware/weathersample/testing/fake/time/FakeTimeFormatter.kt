package com.francescsoftware.weathersample.testing.fake.time

import com.francescsoftware.weathersample.core.time.api.TimeFormatter
import java.util.Calendar
import java.util.Date

/** An implementation of [TimeFormatter] to be used in tests */
class FakeTimeFormatter : TimeFormatter {

    /** @{inheritDoc} */
    override fun setToMidnight(date: Date): Date = Calendar.getInstance().let { calendar ->
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.time
    }

    /** @{inheritDoc} */
    override fun formatDayHour(date: Date): String = date.toString()

    /** @{inheritDoc} */
    override fun formatDayWithDayOfWeek(date: Date): String = date.toString()

    /** @{inheritDoc} */
    override fun formatDay(date: Date): String = date.toString()

    /** @{inheritDoc} */
    override fun formatHour(date: Date): String = date.toString()
}
