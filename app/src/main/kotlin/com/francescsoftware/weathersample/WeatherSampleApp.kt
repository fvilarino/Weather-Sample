package com.francescsoftware.weathersample

import android.app.Application
import timber.log.Timber

class WeatherSampleApp : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.factory().create(this).apply { inject(this@WeatherSampleApp) }
        Timber.plant(Timber.DebugTree())
    }
}
