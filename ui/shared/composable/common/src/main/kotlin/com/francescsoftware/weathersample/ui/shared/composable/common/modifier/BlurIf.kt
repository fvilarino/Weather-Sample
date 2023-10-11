package com.francescsoftware.weathersample.ui.shared.composable.common.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val BlurRadius = 4.dp

fun Modifier.blurIf(
    blur: Boolean,
    radius: Dp = BlurRadius,
) = then(
    if (blur) Modifier.blur(radius = radius) else Modifier,
)
