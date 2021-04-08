package com.francescsoftware.weathersample.feature.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.francescsoftware.weathersample.R
import com.francescsoftware.weathersample.databinding.FragmentCityBinding
import com.francescsoftware.weathersample.feature.common.mvi.MviLifecycleObserver
import com.francescsoftware.weathersample.feature.common.mvi.MviLifecycleView
import com.francescsoftware.weathersample.feature.common.recyclerview.VerticalSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview

@FlowPreview
@AndroidEntryPoint
class CityFragment : Fragment(), MviLifecycleView<CityState, CityEvent> {

    private lateinit var binding: FragmentCityBinding
    private lateinit var mviLifecycleObserver: MviLifecycleObserver<CityState, CityEvent, CityMviIntent>
    private val viewModel: CityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCityBinding.inflate(
            inflater,
            container,
            false
        )
        mviLifecycleObserver = MviLifecycleObserver(this, viewModel).also {
            lifecycle.addObserver(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycle.addObserver(viewModel)
        setupList()
        binding.cityInput.doAfterTextChanged { text ->
            viewModel.onIntent(CityMviIntent.PrefixUpdated(text?.toString().orEmpty()))
        }
    }

    override fun onState(state: CityState) {
        binding.state = state
    }

    private fun setupList() {
        binding.resultsList.adapter = CitiesListAdapter()
        binding.resultsList.setHasFixedSize(true)
        binding.resultsList.addItemDecoration(
            VerticalSpaceItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.margin_double
                )
            )
        )
    }
}
