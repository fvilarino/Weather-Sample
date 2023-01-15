package com.francescsoftware.weathersample.styles

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(widthDp = 420, showBackground = true)
@Preview(widthDp = 420, uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
annotation class WidgetPreviews

@Preview(widthDp = 420, heightDp = 720, showBackground = true, group = "Phone")
@Preview(
    widthDp = 420,
    heightDp = 720,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true, group = "Phone",
)
annotation class PhonePreviews

@Preview(widthDp = 800, heightDp = 540, showBackground = true, group = "Tablet")
@Preview(
    widthDp = 800,
    heightDp = 540,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true, group = "Tablet",
)
annotation class TabletPreviews
