package com.francescsoftware.weathersample.feature.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.francescsoftware.weathersample.databinding.ViewholderCitySerachResultBinding
import com.francescsoftware.weathersample.feature.common.recyclerview.BaseAdapter

class CitiesListAdapter : BaseAdapter<CityResultModel, CitiesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderCitySerachResultBinding.inflate(
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
        private val binding: ViewholderCitySerachResultBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: CityResultModel) {
            binding.model = model
            binding.executePendingBindings()
        }
    }
}
