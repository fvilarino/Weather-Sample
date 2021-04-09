package com.francescsoftware.weathersample.presentation.feature.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.francescsoftware.weathersample.presentation.feature.databinding.ViewholderCitySearchResultBinding
import com.francescsoftware.weathersample.presentation.shared.recyclerview.BaseAdapter

class CitiesListAdapter : BaseAdapter<CityResultModel, CitiesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderCitySearchResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(
        private val binding: ViewholderCitySearchResultBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: CityResultModel) {
            binding.model = model
            binding.executePendingBindings()
        }
    }
}
