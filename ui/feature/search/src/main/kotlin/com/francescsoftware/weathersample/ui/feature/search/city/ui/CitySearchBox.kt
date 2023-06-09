package com.francescsoftware.weathersample.ui.feature.search.city.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationCity
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.TextFieldValue
import com.francescsoftware.weathersample.ui.feature.search.R
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
    val searchBoxContentDescription = stringResource(
        id = R.string.content_description_cities_search_box,
    )
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .semantics { contentDescription = searchBoxContentDescription }
            .onFocusChanged { focusState ->
                if (focusState.isFocused) onQueryFocused()
            },
        label = { Text(text = stringResource(id = R.string.search_city_hint)) },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.LocationCity,
                contentDescription = null,
            )
        },
        trailingIcon = {
            IconButton(onClick = onClearQuery) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(id = R.string.content_description_clear),
                )
            }
        }
    )
}

@WidgetPreviews
@Composable
private fun PreviewCitiesSearchBox() {
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
