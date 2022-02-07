package com.francescsoftware.weathersample.shared.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.francescsoftware.weathersample.styles.MarginDouble

@Composable
fun AppBar(
    title: String,
    @DrawableRes iconId: Int,
    iconContentDescription: String?,
    onIconClick: () -> Unit,
) {
    TopAppBar {
        if (iconId != 0) {
            IconButton(onClick = onIconClick) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = iconContentDescription,
                )
            }
        }
        Text(
            text = title,
            modifier = Modifier.padding(start = MarginDouble),
        )
    }
}
