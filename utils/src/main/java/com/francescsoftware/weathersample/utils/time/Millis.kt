package com.francescsoftware.weathersample.utils.time

inline class Millis(val value: Long) {
    companion object {
        fun convert(seconds: Seconds) = Millis(seconds.value * 1000L)
    }
}

inline class Seconds(val value: Long) {
    fun milliseconds(): Long = Millis.convert(this).value

    companion object {
        fun convert(millis: Millis) = Seconds(millis.value / 1000L)
    }
}
