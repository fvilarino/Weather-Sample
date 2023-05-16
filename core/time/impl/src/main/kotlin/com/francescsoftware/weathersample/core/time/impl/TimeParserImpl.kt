package com.francescsoftware.weathersample.core.time.impl

import com.francescsoftware.weathersample.core.time.api.Iso8601Date
import com.francescsoftware.weathersample.core.time.api.Iso8601DateTime
import com.francescsoftware.weathersample.core.time.api.TimeParser
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

private const val DateFormat = "yyyy-MM-dd"
private const val DateTimeFormat = "yyyy-MM-dd HH:mm"
internal class TimeParserImpl @Inject constructor() : TimeParser {
    override fun parseDate(date: Iso8601Date): Date {
        val inputFormat = SimpleDateFormat(DateFormat, Locale.US)
        return checkNotNull(inputFormat.parse(date.date))
    }

    override fun parseDate(date: Iso8601DateTime): Date {
        val inputFormat = SimpleDateFormat(DateTimeFormat, Locale.US)
        return checkNotNull(inputFormat.parse(date.date))
    }
}
