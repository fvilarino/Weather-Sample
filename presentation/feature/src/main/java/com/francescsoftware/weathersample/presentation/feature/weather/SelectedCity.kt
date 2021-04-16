package com.francescsoftware.weathersample.presentation.feature.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectedCity(
    val name: String,
    val country: String,
    val countryCode: String,
) : Parcelable
