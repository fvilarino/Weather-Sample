package com.francescsoftware.weathersample.ui.shared.styles

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

/** Preview configuration for a simple composable */
@Preview(widthDp = 360, showBackground = true)
@Preview(widthDp = 360, uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
annotation class WidgetPreviews

/** Preview configuration for a phone */
@Preview(device = Devices.PIXEL_3A_XL, showBackground = true, group = "Phone")
@Preview(
    device = Devices.PIXEL_3A_XL,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    group = "Phone",
)
annotation class PhonePreviews

/** Preview configuration for a tablet */
@Preview(device = Devices.PIXEL_C, showBackground = true, group = "Tablet")
@Preview(device = Devices.PIXEL_C, uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, group = "Tablet")
annotation class TabletPreviews
