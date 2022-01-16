package com.francescsoftware.weathersample.lookup.impl

import com.francescsoftware.weathersample.lookup.api.StringLookup
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
internal abstract class LookupModule {
    @Binds
    @Reusable
    abstract fun bindStringLookup(stringLookupImpl: StringLookupImpl): StringLookup
}
