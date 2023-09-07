package com.francescsoftware.weathersample.core.time.api

import java.time.Instant
import java.time.ZonedDateTime

/** Time formatting utilities */
interface TimeFormatter {
    /**
     * Returns a String representation of the [Instant] in argument, formatted as EEE MMMM, dd - for instance, Friday
     * February 10
     */
    fun formatDayWithDayOfWeek(zonedDateTime: ZonedDateTime): String

    /** Returns a String representation of the [Instant] in argument, formatted as HH;MM - for instance, 20:34 */
    fun formatHour(zonedDateTime: ZonedDateTime): String
}
