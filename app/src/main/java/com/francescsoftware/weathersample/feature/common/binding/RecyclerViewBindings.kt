package com.francescsoftware.weathersample.feature.common.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.francescsoftware.weathersample.feature.common.recyclerview.BaseAdapter
import com.francescsoftware.weathersample.feature.common.recyclerview.Diffable

@Suppress("UNCHECKED_CAST")
@BindingAdapter("recyclerViewItems")
fun  <T : Diffable> bindRecyclerViewItems(recyclerView: RecyclerView, items: List<T>?) {
    if (items != null) {
        (recyclerView.adapter as? BaseAdapter<T, *>)?.update(items)
    }
}
