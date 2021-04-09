package com.francescsoftware.weathersample.presentation.shared.binding

import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleIf")
fun bindVisibleIf(view: View, visible: Boolean) {
    view.isVisible = visible
}

@BindingAdapter("invisibleIf")
fun bindInvisibleIf(view: View, visible: Boolean) {
    view.isInvisible = visible
}

@BindingAdapter("goneIf")
fun bindGoneIf(view: View, visible: Boolean) {
    view.isGone = visible
}
