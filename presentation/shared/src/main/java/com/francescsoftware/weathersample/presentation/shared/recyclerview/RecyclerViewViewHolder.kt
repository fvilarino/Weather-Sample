package com.francescsoftware.weathersample.presentation.shared.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewViewHolder<T : Diffable>(
    protected val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(bindingItem: T)
}
