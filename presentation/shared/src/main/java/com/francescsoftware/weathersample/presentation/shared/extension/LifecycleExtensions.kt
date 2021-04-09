package com.francescsoftware.weathersample.presentation.shared.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner

val LifecycleOwner.locateActivity: FragmentActivity?
    get() = when (this) {
        is FragmentActivity -> this
        is Fragment -> activity
        else -> null
    }
