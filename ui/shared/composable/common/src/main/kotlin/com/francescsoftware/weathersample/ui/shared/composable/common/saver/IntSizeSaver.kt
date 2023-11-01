package com.francescsoftware.weathersample.ui.shared.composable.common.saver

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.ui.unit.IntSize

fun intSizeSaver() = object : Saver<IntSize, List<Int>> {
    override fun restore(value: List<Int>): IntSize = IntSize(
        width = value[0],
        height = value[1],
    )

    override fun SaverScope.save(value: IntSize): List<Int> = listOf(value.width, value.height)
}
