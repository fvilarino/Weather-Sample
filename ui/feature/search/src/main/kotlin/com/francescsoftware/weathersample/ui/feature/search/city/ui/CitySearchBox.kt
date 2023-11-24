package com.francescsoftware.weathersample.ui.feature.search.city.ui

import android.Manifest
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.TextFieldValue
import com.francescsoftware.weathersample.ui.feature.search.R
import com.francescsoftware.weathersample.ui.shared.composable.common.overlay.DialogOverlay
import com.francescsoftware.weathersample.ui.shared.composable.common.overlay.DialogResult
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.slack.circuit.overlay.LocalOverlayHost
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun CitiesSearchBox(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onClearQuery: () -> Unit,
    onLocationClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val locationPermission = rememberPermissionState(permission = Manifest.permission.ACCESS_COARSE_LOCATION)
    val searchBoxContentDescription = stringResource(
        id = R.string.test_tag_cities_search_box,
    )
    val overlayHost = LocalOverlayHost.current
    val scope = rememberCoroutineScope()
    var locationRequested by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = locationRequested, key2 = locationPermission.status) {
        if (locationRequested && locationPermission.status.isGranted) {
            onLocationClick()
        }
        locationRequested = false
    }
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .semantics { testTag = searchBoxContentDescription },
        label = { Text(text = stringResource(id = R.string.search_city_hint)) },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.LocationCity,
                contentDescription = null,
            )
        },
        trailingIcon = {
            Row {
                IconButton(onClick = onClearQuery) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(id = R.string.content_description_clear),
                    )
                }
                IconButton(
                    onClick = {
                        if (locationPermission.status.isGranted) {
                            onLocationClick()
                        } else {
                            scope.launch {
                                val result = overlayHost.show(
                                    locationDialogOverlay(locationPermission.status.shouldShowRationale),
                                )
                                if (result == DialogResult.Confirm) {
                                    locationPermission.launchPermissionRequest()
                                    locationRequested = true
                                }
                            }
                        }
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.MyLocation,
                        contentDescription = stringResource(id = R.string.content_description_my_location),
                    )
                }
            }
        },
    )
}

private fun locationDialogOverlay(
    showRationale: Boolean,
) = DialogOverlay(
    confirmButtonText = {
        Text(
            text = stringResource(id = android.R.string.ok),
        )
    },
    dismissButtonText = {
        Text(
            text = stringResource(id = android.R.string.cancel),
        )
    },
    text = {
        Text(
            text = stringResource(
                if (showRationale) {
                    R.string.coarse_location_rationale_request
                } else {
                    R.string.coarse_location_request
                },
            ),
        )
    },
)

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
                onLocationClick = {},
                modifier = Modifier.padding(all = MarginDouble),
            )
        }
    }
}
