package com.francescsoftware.weathersample.presentation.shared.recyclerview

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

abstract class RecyclerViewViewHolder<T : Diffable>(
    protected val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(bindingItem: T)
}

interface RecyclerViewBindingItem : Diffable {
    val type: Int

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder<*>
}

class TypedBaseAdapter<T : RecyclerViewBindingItem> :
    RecyclerView.Adapter<RecyclerViewViewHolder<T>>() {

    private val differ: AsyncListDiffer<T> by lazy(LazyThreadSafetyMode.NONE) {
        AsyncListDiffer(this, Differ<T>())
    }

    protected var items: List<T> by Delegates.observable(emptyList()) { _, _, new ->
        differ.submitList(new)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder<T> =
        items.first { it.type == viewType }
            .onCreateViewHolder(parent, viewType) as RecyclerViewViewHolder<T>

    override fun onBindViewHolder(holder: RecyclerViewViewHolder<T>, position: Int) {
        holder.bind(items[position])
    }

    fun update(items: List<T>) {
        this.items = items
    }
}
