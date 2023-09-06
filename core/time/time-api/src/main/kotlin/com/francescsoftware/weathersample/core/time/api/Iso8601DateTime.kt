package com.francescsoftware.weathersample.core.time.api

import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Represents a date in the ISO8601 format, consisting of year, month, day,
 * 24h and minutes
 *
 * @property date the string representation of the date
 */
@JvmInline
value class Iso8601DateTime private constructor(
    val date: String,
) {
    /**
     * Converts this [Iso8601DateTime] to a [ZonedDateTime]
     *
     * @param zoneId the [ZoneId] to use for the conversion
     * @return a [ZonedDateTime] representing this [Iso8601DateTime]
     */
    fun toZonedDateTime(
        zoneId: ZoneId = ZoneId.of(ZoneId.systemDefault().id)
    ): ZonedDateTime {
        val matchResult = requireNotNull(Matcher.matchEntire(date))
        return ZonedDateTime.of(
            matchResult.groupValues[YearIndex].toInt(),
            matchResult.groupValues[MonthIndex].toInt(),
            matchResult.groupValues[DayIndex].toInt(),
            matchResult.groupValues[HourIndex].toInt(),
            matchResult.groupValues[MinuteIndex].toInt(),
            0,
            0,
            zoneId,
        )
    }

    companion object {
        private val Matcher = Regex("^(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2})\$")
        private const val YearIndex = 1
        private const val MonthIndex = YearIndex + 1
        private const val DayIndex = MonthIndex + 1
        private const val HourIndex = DayIndex + 1
        private const val MinuteIndex = HourIndex + 1
        private const val February = 2
        private val MonthRange = 1..12
        private val DayRange = listOf(
            1..31,
            1..28,
            1..31,
            1..30,
            1..31,
            1..30,
            1..31,
            1..31,
            1..30,
            1..31,
            1..30,
            1..31,
        )
        private val FebruaryLeapRange = 1..29
        private val HourRange = 0..23
        private val MinuteRange = 0..59

        /**
         * Constructs a [Iso8601DateTime] from the incoming string if valid, throw
         * TimeParsingException otherwise
         */
        operator fun invoke(date: String): Iso8601DateTime {
            val matchResult = Matcher.matchEntire(date)
            return if (matchResult != null) {
                val year = matchResult.groupValues[YearIndex].toInt()
                val month = matchResult.groupValues[MonthIndex].toInt()
                val tempDate = LocalDate.of(year, 1, 1)
                val dayRange = if (tempDate.isLeapYear && month == February) {
                    FebruaryLeapRange
                } else {
                    DayRange.getOrNull(month - 1)
                }
                val day = matchResult.groupValues[DayIndex].toInt()
                val hour = matchResult.groupValues[HourIndex].toInt()
                val minute = matchResult.groupValues[MinuteIndex].toInt()
                @Suppress("ComplexCondition")
                if (
                    month in MonthRange &&
                    dayRange != null &&
                    day in dayRange &&
                    hour in HourRange &&
                    minute in MinuteRange
                ) {
                    Iso8601DateTime(date)
                } else {
                    throw TimeParsingException("Invalid date [$date], expected format is YYYY-MM-DD HH:MM")
                }
            } else {
                throw TimeParsingException("Invalid date [$date], expected format is YYYY-MM-DD HH:MM")
            }
        }
    }
}

/** Exception thrown when parsing an invalid date */
class TimeParsingException(message: String) : RuntimeException(message)
