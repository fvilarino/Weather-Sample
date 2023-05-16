package com.francescsoftware.weathersample.ui.feature.city.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.francescsoftware.weathersample.ui.feature.city.R
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews

@Composable
internal fun CitiesSearchBox(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onClearQuery: () -> Unit,
    onQueryFocused: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.onFocusChanged { focusState ->
            if (focusState.isFocused) onQueryFocused()
        },
        label = { Text(text = stringResource(id = R.string.search_city_hint)) },
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(
                    id = com.francescsoftware.weathersample.ui.shared.assets.R.drawable.ic_baseline_location_city_24
                ),
                contentDescription = null,
            )
        },
        trailingIcon = {
            IconButton(onClick = onClearQuery) {
                Icon(
                    painter = painterResource(
                        id = com.francescsoftware.weathersample.ui.shared.assets.R.drawable.ic_baseline_clear_24
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
            color = MaterialTheme.colorScheme.background,
        ) {
            var query by remember {
                mutableStateOf(TextFieldValue())
            }
            CitiesSearchBox(
                query = query,
                onQueryChange = { query = it },
                onClearQuery = { query = TextFieldValue() },
                onQueryFocused = {},
                modifier = Modifier.padding(all = MarginDouble)
            )
        }
    }
}
