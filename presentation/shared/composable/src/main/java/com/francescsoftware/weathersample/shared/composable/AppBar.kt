package com.francescsoftware.weathersample.shared.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.francescsoftware.weathersample.styles.MarginDouble

@Composable
fun AppBar(
    @StringRes titleId: Int,
    @DrawableRes iconId: Int,
    onIconClick: () -> Unit,
) {
    TopAppBar {
        if (iconId != 0) {
            IconButton(onClick = onIconClick) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                )
            }
        }
        Text(
            text = stringResource(id = titleId),
            modifier = Modifier.padding(start = MarginDouble)
        )
    }
}
