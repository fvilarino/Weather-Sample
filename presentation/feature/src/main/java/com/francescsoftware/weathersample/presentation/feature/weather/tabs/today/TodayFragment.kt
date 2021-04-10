package com.francescsoftware.weathersample.presentation.feature.weather.tabs.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.francescsoftware.weathersample.presentation.feature.databinding.FragmentTodayBinding
import com.francescsoftware.weathersample.presentation.feature.weather.SelectedCityProvider
import com.francescsoftware.weathersample.presentation.shared.mvi.MviLifecycleObserver
import com.francescsoftware.weathersample.presentation.shared.mvi.MviLifecycleView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodayFragment : Fragment(), MviLifecycleView<TodayState, TodayEvent> {

    private val viewModel: TodayViewModel by viewModels()
    private lateinit var binding: FragmentTodayBinding
    private lateinit var mviLifecycleObserver: MviLifecycleObserver<TodayState, TodayEvent, TodayMviIntent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodayBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        mviLifecycleObserver = MviLifecycleObserver(this, viewModel).also {
            lifecycle.addObserver(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycle.addObserver(viewModel)
        val city = (parentFragment as SelectedCityProvider).selectedCity
        viewModel.onIntent(TodayMviIntent.Load(city))
        binding.retryButton.setOnClickListener { viewModel.onIntent(TodayMviIntent.Retry) }
    }

    override fun onState(state: TodayState) {
        binding.state = state
    }
}
