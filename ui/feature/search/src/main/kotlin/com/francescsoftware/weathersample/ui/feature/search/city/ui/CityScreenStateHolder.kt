package com.francescsoftware.weathersample.ui.feature.search.city.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

@Stable
internal interface CityScreenStateHolder {
    val query: TextFieldValue
    fun onQueryUpdated(query: TextFieldValue)
    fun onClearQuery()
}

@Composable
internal fun rememberCityScreenStateHolder(
    query: String = "",
    selectionStart: Int = 0,
    selectionEnd: Int = 0,
): CityScreenStateHolder = rememberSaveable(saver = CityScreenStateHolderImpl.Saver) {
    CityScreenStateHolderImpl(
        query = query,
        selectionStart = selectionStart,
        selectionEnd = selectionEnd,
    )
}

@Stable
private class CityScreenStateHolderImpl(
    query: String,
    selectionStart: Int,
    selectionEnd: Int,
) : CityScreenStateHolder {
    override var query by mutableStateOf(
        TextFieldValue(
            text = query,
            selection = TextRange(
                start = selectionStart,
                end = selectionEnd,
            )
        )
    )
        private set

    override fun onQueryUpdated(query: TextFieldValue) {
        this.query = query
    }

    override fun onClearQuery() {
        query = TextFieldValue()
    }

    companion object {
        val Saver = Saver<CityScreenStateHolderImpl, List<*>>(
            save = { stateHolder ->
                listOf(
                    stateHolder.query.annotatedString.text,
                    stateHolder.query.selection.start,
                    stateHolder.query.selection.end,
                )
            },
            restore = { args ->
                CityScreenStateHolderImpl(
                    query = args[0] as String,
                    selectionStart = args[1] as Int,
                    selectionEnd = args[2] as Int,
                )
            }
        )
    }
}
