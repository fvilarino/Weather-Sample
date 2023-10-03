package com.francescsoftware.weathersample

import android.app.Application
import timber.log.Timber

class WeatherSampleApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
