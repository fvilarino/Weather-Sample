package com.francescsoftware.weathersample.feature.common.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner

val LifecycleOwner.localteActivity: FragmentActivity?
    get() = when (this) {
        is FragmentActivity -> this
        is Fragment -> activity
        else -> null
    }
