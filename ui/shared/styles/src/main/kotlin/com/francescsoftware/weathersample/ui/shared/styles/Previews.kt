package com.francescsoftware.weathersample.ui.shared.styles

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

val PhoneDpSize = DpSize(width = 412.dp, height = 915.dp)
val TabletDpSize = DpSize(width = 800.dp, height = 640.dp)

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

/** Preview configuration for a phone in landscape */
@Preview(
    device = "spec: width=411dp, height=891dp, dpi=420, isRound=false, chinSize=0dp, orientation=landscape",
    group = "Phone",
)
@Preview(
    device = "spec: width=411dp, height=891dp, dpi=420, isRound=false, chinSize=0dp, orientation=landscape",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    group = "Phone",
)
annotation class LandscapePhonePreviews

/** Preview configuration for a tablet */
@Preview(device = Devices.PIXEL_C, showBackground = true, group = "Tablet")
@Preview(device = Devices.PIXEL_C, uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, group = "Tablet")
annotation class TabletPreviews
