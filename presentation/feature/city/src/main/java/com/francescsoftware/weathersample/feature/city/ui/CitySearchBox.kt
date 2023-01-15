package com.francescsoftware.weathersample.feature.city.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.francescsoftware.weathersample.feature.city.R
import com.francescsoftware.weathersample.feature.city.viewmodel.CityState
import com.francescsoftware.weathersample.styles.MarginDouble
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.styles.WidgetPreviews

@Composable
internal fun CitiesSearchBox(
    state: CityState,
    onQueryChange: (TextFieldValue) -> Unit,
    onClearQuery: () -> Unit,
    onQueryFocused: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = state.query,
        onValueChange = onQueryChange,
        modifier = modifier.onFocusChanged { focusState ->
            if (focusState.isFocused) onQueryFocused()
        },
        label = { Text(text = stringResource(id = R.string.search_city_hint)) },
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(
                    id = com.francescsoftware.weathersample.shared.assets.R.drawable.ic_baseline_location_city_24
                ),
                contentDescription = null,
            )
        },
        trailingIcon = {
            IconButton(onClick = onClearQuery) {
                Icon(
                    painter = painterResource(
                        id = com.francescsoftware.weathersample.shared.assets.R.drawable.ic_baseline_clear_24
                    ),
                    contentDescription = stringResource(id = R.string.content_description_clear),
                )
            }
        }
    )
}

@WidgetPreviews
@Composable
fun CitiesSearchBoxPreview() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colors.background,
        ) {
            var state by remember { mutableStateOf(CityState.initial) }
            CitiesSearchBox(
                state = state,
                onQueryChange = { state = state.copy(query = it) },
                onClearQuery = { state = state.copy(query = TextFieldValue()) },
                onQueryFocused = {},
                modifier = Modifier.padding(all = MarginDouble)
            )
        }
    }
}
