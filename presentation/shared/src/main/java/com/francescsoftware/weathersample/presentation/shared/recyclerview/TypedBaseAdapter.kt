package com.francescsoftware.weathersample.presentation.shared.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

class TypedBaseAdapter<T : RecyclerViewBindingItem> :
    RecyclerView.Adapter<RecyclerViewViewHolder<T>>() {

    private val differ: AsyncListDiffer<T> by lazy(LazyThreadSafetyMode.NONE) {
        AsyncListDiffer(this, Differ<T>())
    }

    protected var items: List<T> by Delegates.observable(emptyList()) { _, _, new ->
        differ.submitList(new)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun getItemViewType(position: Int): Int = items[position].type

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewViewHolder<T> =
        items.first { it.type == viewType }
            .onCreateViewHolder(parent, viewType) as RecyclerViewViewHolder<T>

    override fun onBindViewHolder(holder: RecyclerViewViewHolder<T>, position: Int) {
        holder.bind(items[position])
    }

    fun update(items: List<T>) {
        this.items = items
    }
}
