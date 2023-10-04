package com.francescsoftware.weathersample.ui.shared.composable.common.saver

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

class ImmutableListSaver<T> : Saver<ImmutableList<T>, List<T>> {
    override fun restore(value: List<T>): ImmutableList<T> = value.toPersistentList()

    override fun SaverScope.save(value: ImmutableList<T>): List<T> = value.toList()
}
