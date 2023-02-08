package com.francescsoftware.weathersample.deviceclass

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
class DeviceClassProvider : PreviewParameterProvider<DeviceClass> {
    override val values: Sequence<DeviceClass> = sequenceOf(
        DeviceClass.Compact,
        DeviceClass.Medium,
        DeviceClass.Expanded,
    )
}
