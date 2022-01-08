package com.francescsoftware.weathersample.presentation.feature.search

import com.francescsoftware.weathersample.interactor.city.api.GetCitiesInteractor
import com.francescsoftware.weathersample.presentation.feature.navigator.FakeNavigator
import com.francescsoftware.weathersample.presentation.feature.navigator.Navigator
import com.francescsoftware.weathersample.presentation.shared.lookup.StringLookup
import com.francescsoftware.weathersample.testing.CoroutinesTestExtension
import com.francescsoftware.weathersample.testing.InstantTaskExecutorExtension
import com.francescsoftware.weathersample.presentation.shared.lookup.FakeStringLookup
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(InstantTaskExecutorExtension::class, CoroutinesTestExtension::class)
class CityViewModelTest {

    @Test
    fun dummyTest() {
        val viewModel = getViewModel()
        Assert.assertTrue(true)
    }

    private fun getViewModel(
        getCitiesInteractor: GetCitiesInteractor = FakeGetCitiesInteractor(),
        navigator: Navigator = FakeNavigator(),
        stringLookup: StringLookup = FakeStringLookup(),
    ): CityViewModel = CityViewModel(
        getCitiesInteractor = getCitiesInteractor,
        navigator = navigator,
        stringLookup = stringLookup,
    )
}
