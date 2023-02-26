package com.francescsoftware.weathersample.time.api

import java.util.*

/** Time formatting utilities */
interface TimeFormatter {
    /**
     * Returns a [Date] on the same day as the incoming argument but with the hours, minutes and seconds set to 0
     * (midnight).
     */
    fun setToMidnight(date: Date): Date

    /**
     * Returns a String representation of the [Date] in argument, formatted as MMMM, dd HH:mm - for instance, April, 14
     * 16:24
     */
    fun formatDayHour(date: Date): String

    /**
     * Returns a String representation of the [Date] in argument, formatted as EEE MMMM, dd - for instance, Friday
     * February 10
     */
    fun formatDayWithDayOfWeek(date: Date): String

    /** Returns a String representation of the [Date] in argument, formatted as MMMM, dd - for instance, April, 14 */
    fun formatDay(date: Date): String

    /** Returns a String representation of the [Date] in argument, formatted as HH;MM - for instance, 20:34 */
    fun formatHour(date: Date): String
}
