package com.francescsoftware.weathersample.ui.shared.composable.common.extension

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.IntSize

fun IntSize.toRect() = Rect(
    left = 0f,
    top = 0f,
    right = width.toFloat(),
    bottom = height.toFloat(),
)
