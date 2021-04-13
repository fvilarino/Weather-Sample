package com.francescsoftware.weathersample.presentation.feature.weather.tabs.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.francescsoftware.weathersample.presentation.feature.R
import com.francescsoftware.weathersample.presentation.feature.databinding.FragmentForecastBinding
import com.francescsoftware.weathersample.presentation.feature.weather.SelectedCityProvider
import com.francescsoftware.weathersample.presentation.feature.weather.tabs.today.TodayMviIntent
import com.francescsoftware.weathersample.presentation.shared.mvi.MviLifecycleObserver
import com.francescsoftware.weathersample.presentation.shared.mvi.MviLifecycleView
import com.francescsoftware.weathersample.presentation.shared.recyclerview.RecyclerViewBindingItem
import com.francescsoftware.weathersample.presentation.shared.recyclerview.TypedBaseAdapter
import com.francescsoftware.weathersample.presentation.shared.recyclerview.VerticalSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastFragment : Fragment(), MviLifecycleView<ForecastState, ForecastEvent> {

    private lateinit var binding: FragmentForecastBinding
    private val viewModel: ForecastViewModel by viewModels()
    private lateinit var mviLifecycleObserver: MviLifecycleObserver<ForecastState, ForecastEvent, ForecastMviIntent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForecastBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        mviLifecycleObserver = MviLifecycleObserver(this, viewModel).also {
            lifecycle.addObserver(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycle.addObserver(viewModel)
        val city = (parentFragment as SelectedCityProvider).selectedCity
        viewModel.onIntent(ForecastMviIntent.Load(city))
        val adapter = TypedBaseAdapter<RecyclerViewBindingItem>()
        binding.forecastList.adapter = adapter
        binding.forecastList.setHasFixedSize(true)
        binding.forecastList.addItemDecoration(
            VerticalSpaceItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.margin_double
                )
            )
        )
        binding.retryButton.setOnClickListener { viewModel.onIntent(ForecastMviIntent.Retry) }
    }

    override fun onState(state: ForecastState) {
        binding.state = state
    }
}
