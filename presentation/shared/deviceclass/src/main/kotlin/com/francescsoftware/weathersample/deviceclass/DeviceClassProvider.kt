package com.francescsoftware.weathersample.deviceclass

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

/** Preview parameter provider for [DeviceClass] */
class DeviceClassProvider : PreviewParameterProvider<DeviceClass> {

    /** Collection of [DeviceClass] values provided by this preview paramter provider. */
    override val values: Sequence<DeviceClass> = sequenceOf(
        DeviceClass.Compact,
        DeviceClass.Medium,
        DeviceClass.Expanded,
    )
}
