package com.francescsoftware.weathersample.presentation.shared.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imageDrawableId")
fun bindImageDrawableId(imageView: ImageView, id: Int) {
    imageView.setImageResource(id)
}
