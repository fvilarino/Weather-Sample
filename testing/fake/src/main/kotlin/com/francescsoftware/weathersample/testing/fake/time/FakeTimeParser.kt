package com.francescsoftware.weathersample.testing.fake.time

import com.francescsoftware.weathersample.core.time.api.Iso8601Date
import com.francescsoftware.weathersample.core.time.api.Iso8601DateTime
import com.francescsoftware.weathersample.core.time.api.TimeParser
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/** An implementation of [TimeParser] to be used in tests */
class FakeTimeParser : TimeParser {

    /** @{inheritDoc} */
    override fun parseDate(date: Iso8601Date): Date {
        val format = "yyyy-MM-dd"
        val inputFormat = SimpleDateFormat(format, Locale.US)
        return checkNotNull(inputFormat.parse(date.date))
    }

    /** @{inheritDoc} */
    override fun parseDate(date: Iso8601DateTime): Date {
        val format = "yyyy-MM-dd HH:mm"
        val inputFormat = SimpleDateFormat(format, Locale.US)
        return checkNotNull(inputFormat.parse(date.date))
    }
}
