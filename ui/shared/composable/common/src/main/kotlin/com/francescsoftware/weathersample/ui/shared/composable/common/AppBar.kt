package com.francescsoftware.weathersample.ui.shared.composable.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * App bar for all screens
 *
 * @param title - label to display on the app bar
 * @param icon - navigation icon to display on the left corner
 * @param iconContentDescription - accessibility label for the icon
 * @param onIconClick - called when the [icon] is clicked
 * @param modifier - the [Modifier] to apply to this app bar
 * @param actions - a list of action menu options to render on the right side of the app bar
 */
@Composable
fun AppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        navigationIcon = navigationIcon,
        title = {
            Text(
                text = title,
            )
        },
        actions = actions,
        modifier = modifier,
    )
}
