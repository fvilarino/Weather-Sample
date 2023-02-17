package com.francescsoftware.weathersample.shared.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

/**
 * App bar for all screens
 *
 * @param title - label to display on the app bar
 * @param iconId - navigation icon to display on the left corner
 * @param iconContentDescription - accessibility label for the icon
 * @param onIconClick - called when the [iconId] is clicked
 * @param modifier - the [Modifier] to apply to this app bar
 * @param actions - a list of action menu options to render on the right side of the app bar
 */
@Composable
fun AppBar(
    title: String,
    @DrawableRes iconId: Int,
    iconContentDescription: String?,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        navigationIcon = {
            if (iconId != 0) {
                IconButton(onClick = onIconClick) {
                    Icon(
                        painter = painterResource(id = iconId),
                        contentDescription = iconContentDescription,
                    )
                }
            }
        },
        title = {
            Text(
                text = title,
            )
        },
        actions = actions,
        modifier = modifier,
    )
}
