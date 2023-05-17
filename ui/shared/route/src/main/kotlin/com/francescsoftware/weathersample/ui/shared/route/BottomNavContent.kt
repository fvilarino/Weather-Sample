package com.francescsoftware.weathersample.ui.shared.route

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Payload for bottom navigation destinations
 *
 * @property labelId the label to display on the bottom nav bar
 * @property icon the icon to display on the bottom nav bar
 * @property contentDescriptionId the content description associated with this nav bar item
 */
data class BottomNavContent(
    @get: StringRes
    val labelId: Int,

    val icon: ImageVector,

    @get: StringRes
    val contentDescriptionId: Int
)
