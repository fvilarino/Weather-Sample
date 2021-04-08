package com.francescsoftware.weathersample.feature.common.recyclerview

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

abstract class BaseAdapter<T : Diffable, V : RecyclerView.ViewHolder> : RecyclerView.Adapter<V>() {

    private val differ: AsyncListDiffer<T> by lazy(LazyThreadSafetyMode.NONE) {
        AsyncListDiffer(this, Differ<T>())
    }

    protected var items: List<T> by Delegates.observable(emptyList()) { _, _, new ->
        differ.submitList(new)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun update(items: List<T>) {
        this.items = items
    }
}

class Differ <T : Diffable> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
}
