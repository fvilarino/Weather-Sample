package com.francescsoftware.weathersample.ui.shared.lookup.impl

import com.francescsoftware.weathersample.ui.shared.lookup.api.StringLookup
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface LookupModule {
    @Binds
    @Reusable
    fun bindStringLookup(stringLookupImpl: StringLookupImpl): StringLookup
}
