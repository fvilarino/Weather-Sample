package com.francescsoftware.weathersample.feature.common.lookup

import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class LookupModule {
    @Binds
    @Reusable
    abstract fun bindStringLookup(stringLookupImpl: StringLookupImpl): StringLookup
}
