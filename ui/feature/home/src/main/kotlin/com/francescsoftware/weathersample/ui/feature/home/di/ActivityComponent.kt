package com.francescsoftware.weathersample.ui.feature.home.di

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.francescsoftware.weathersample.core.injection.ActivityScope
import com.francescsoftware.weathersample.core.injection.SingleIn
import com.francescsoftware.weathersample.ui.feature.home.MainActivity
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.LocalCircuit
import com.slack.circuit.foundation.NavigatorDefaults
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.MergeSubcomponent
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.multibindings.Multibinds

@MergeSubcomponent(ActivityScope::class)
@SingleIn(ActivityScope::class)
interface ActivityComponent {
    fun inject(activity: MainActivity)

    @MergeSubcomponent.Factory
    fun interface Factory {
        fun create(@BindsInstance activity: MainActivity): ActivityComponent
    }
}

@Module
@ContributesTo(ActivityScope::class)
interface ActivityModule {
    @Multibinds
    fun presenterFactories(): Set<Presenter.Factory>

    @Multibinds
    fun viewFactories(): Set<Ui.Factory>

    companion object {
        @Provides
        @SingleIn(ActivityScope::class)
        fun provideCircuit(
            presenterFactories: @JvmSuppressWildcards Set<Presenter.Factory>,
            uiFactories: @JvmSuppressWildcards Set<Ui.Factory>,
        ): Circuit = Circuit.Builder()
            .addPresenterFactories(presenterFactories)
            .addUiFactories(uiFactories)
            .setDefaultNavDecoration(NavigatorDefaults.DefaultDecoration)
            .setOnUnavailableContent { screen, modifier ->
                val circuit = LocalCircuit.current
                BasicText(
                    """
                      Route not available: ${screen.javaClass.name}.
                      Presenter: ${circuit?.presenter(screen, Navigator.NoOp)?.javaClass}
                      UI: ${circuit?.ui(screen)?.javaClass}
                      All presenterFactories: ${circuit?.newBuilder()?.presenterFactories}
                      All uiFactories: ${circuit?.newBuilder()?.uiFactories}
                      """
                        .trimIndent(),
                    modifier
                        .background(Color.Red)
                        .padding(all = MarginDouble),
                    style = TextStyle(color = Color.Yellow),
                )
            }
            .build()
    }
}
