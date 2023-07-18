package com.francescsoftware.weathersample.ui.shared.composable.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * App bar for all screens
 *
 * @param title label to display on the app bar
 * @param modifier the [Modifier] to apply to this app bar
 * @param scrollBehavior the app bar's [TopAppBarScrollBehavior] to handle color changes on scroll
 * @param navigationIcon the navigation icon to display on the left of the app bar
 * @param actions a composable with the action menu options to render on the right side of the app bar
 */
@Composable
fun AppBar(
    title: String,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
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
        scrollBehavior = scrollBehavior,
    )
}
