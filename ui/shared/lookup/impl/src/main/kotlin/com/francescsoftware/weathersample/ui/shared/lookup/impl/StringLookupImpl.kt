package com.francescsoftware.weathersample.ui.shared.lookup.impl

import android.content.Context
import com.francescsoftware.weathersample.ui.shared.lookup.api.StringLookup
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class StringLookupImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : StringLookup {

    override fun getString(id: Int): String = context.getString(id)

    override fun getString(id: Int, vararg formatArgs: Any): String =
        context.getString(id, *formatArgs)
}
