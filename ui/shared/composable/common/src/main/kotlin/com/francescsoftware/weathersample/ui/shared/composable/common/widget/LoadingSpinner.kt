package com.francescsoftware.weathersample.ui.shared.composable.common.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/** A generic loading spinner */
@Composable
fun LoadingSpinner(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        LoadingIndicator(
            animating = true,
            color = MaterialTheme.colorScheme.primary,
            animationType = AnimationType.LazyBounce,
        )
    }
}
