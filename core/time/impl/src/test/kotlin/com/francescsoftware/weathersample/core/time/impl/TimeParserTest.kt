package com.francescsoftware.weathersample.core.time.impl

import com.francescsoftware.weathersample.core.time.api.Iso8601Date
import com.francescsoftware.weathersample.core.time.api.Iso8601DateTime
import com.google.common.truth.Truth
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
        Truth.assertThat(calendar.get(Calendar.YEAR)).isEqualTo(year.toInt())
        Truth.assertThat(calendar.get(Calendar.MONTH) + 1).isEqualTo(month.toInt())
        Truth.assertThat(calendar.get(Calendar.DAY_OF_MONTH)).isEqualTo(day.toInt())
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
        Truth.assertThat(calendar.get(Calendar.YEAR)).isEqualTo(year.toInt())
        Truth.assertThat(calendar.get(Calendar.MONTH) + 1).isEqualTo(month.toInt())
        Truth.assertThat(calendar.get(Calendar.DAY_OF_MONTH)).isEqualTo(day.toInt())
        Truth.assertThat(calendar.get(Calendar.HOUR_OF_DAY)).isEqualTo(hour.toInt())
        Truth.assertThat(calendar.get(Calendar.MINUTE)).isEqualTo(minute.toInt())
    }
}
