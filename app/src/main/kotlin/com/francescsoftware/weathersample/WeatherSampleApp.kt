package com.francescsoftware.weathersample

import android.app.Application
import com.francescsoftware.weathersample.ui.feature.home.di.ActivityComponent
import com.francescsoftware.weathersample.ui.feature.home.di.ActivityComponentFactoryProvider
import timber.log.Timber

class WeatherSampleApp : Application(), ActivityComponentFactoryProvider {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.factory().create(this)
        Timber.plant(Timber.DebugTree())
    }

    override fun getActivityComponentFactory(): ActivityComponent.Factory = appComponent.getActivityComponentFactory()
}
