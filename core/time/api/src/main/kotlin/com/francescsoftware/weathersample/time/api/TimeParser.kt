package com.francescsoftware.weathersample.time.api

import java.util.Date

/**
 * Represents a date in the ISO8601 format, consisting of year, month and day
 *
 * @property date the string representation of the date
 */
@JvmInline
value class Iso8601Date private constructor(val date: String) {
    companion object {
        private val Matcher = Regex("^\\d{4}-\\d{2}-\\d{2}\$")

        /** Constructs a [Iso8601Date] from the incoming string if valid, throw TimeParsingException otherwise */
        operator fun invoke(date: String): Iso8601Date = if (date.matches(Matcher)) {
            Iso8601Date(date)
        } else {
            throw TimeParsingException("Invalid date [$date], expected format is YYYY-MM-DD")
        }
    }
}

/**
 * Represents a date in the ISO8601 format, consisting of year, month, day, 24h and minutes
 *
 * @property date the string representation of the date
 */
@JvmInline
value class Iso8601DateTime private constructor(val date: String) {
    companion object {
        private val Matcher = Regex("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}\$")

        /** Constructs a [Iso8601DateTime] from the incoming string if valid, throw TimeParsingException otherwise */
        operator fun invoke(date: String): Iso8601DateTime = if (date.matches(Matcher)) {
            Iso8601DateTime(date)
        } else {
            throw TimeParsingException("Invalid date [$date], expected format is YYYY-MM-DD HH:MM")
        }
    }
}

/** Exception thrown when parsing an invalid date */
class TimeParsingException(message: String) : RuntimeException(message)

/** Time parsing utilities */
interface TimeParser {
    /**
     * Parses a date in the format YYYY-MM-DD and returns a [Date] representing this date
     *
     * @param date The [Iso8601Date] to parse
     * @return a [Date] corresponding to the [date]
     */
    fun parseDate(date: Iso8601Date): Date

    /**
     * Parses a date in the format YYYY-MM-DD HH:MM and returns a [Date] representing this date
     *
     * @param date The [Iso8601DateTime] to parse
     * @return a [Date] corresponding to the [date]
     */
    fun parseDate(date: Iso8601DateTime): Date
}
