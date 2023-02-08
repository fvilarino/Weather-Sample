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
