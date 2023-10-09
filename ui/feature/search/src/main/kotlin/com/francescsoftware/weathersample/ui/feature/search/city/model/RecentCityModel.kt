package com.francescsoftware.weathersample.ui.feature.search.city.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@JvmInline
@Parcelize
value class RecentCityModel(val name: String) : Parcelable
