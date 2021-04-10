package com.francescsoftware.weathersample.presentation.shared.binding

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.francescsoftware.weathersample.presentation.shared.R

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

@BindingAdapter("animatedVisibility")
fun bindAnimatedVisibility(view: View, visible: Boolean) {
    val toggled = view.isVisible && !visible || !view.isVisible && visible
    if (toggled) {
        (view.parent as? ViewGroup)?.let { parent ->
            val duration = view.resources.getInteger(R.integer.short_animation_duration)
            TransitionManager.beginDelayedTransition(
                parent,
                AutoTransition().apply { setDuration(duration.toLong()) }
            )
        }
        view.isVisible = visible
    }
}
