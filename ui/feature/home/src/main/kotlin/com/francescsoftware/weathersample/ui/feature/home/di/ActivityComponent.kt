package com.francescsoftware.weathersample.ui.feature.home.di

import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.francescsoftware.weathersample.ui.feature.home.MainActivity
import com.squareup.anvil.annotations.MergeSubcomponent
import dagger.BindsInstance
import dagger.Subcomponent

@MergeSubcomponent(ActivityScope::class)
@SingleIn(ActivityScope::class)
interface ActivityComponent {
    fun inject(activity: MainActivity)

    @Subcomponent.Factory
    fun interface Factory {
        fun create(@BindsInstance activity: MainActivity): ActivityComponent
    }
}
