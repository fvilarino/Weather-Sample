package com.francescsoftware.weathersample.presentation.shared.recyclerview

import android.view.ViewGroup

interface RecyclerViewBindingItem : Diffable {
    val type: Int

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder<*>
}
