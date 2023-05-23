package com.francescsoftware.weathersample.ui.feature.home

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

internal object NavigationColors {
    @Composable
    fun navigationSelectedTextColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer

    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant
}
