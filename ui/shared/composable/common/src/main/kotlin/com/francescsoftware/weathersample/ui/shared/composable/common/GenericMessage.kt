package com.francescsoftware.weathersample.ui.shared.composable.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews

/**
 * Displays a message with an optional icon
 *
 * @param message the message to display
 * @param modifier the [Modifier] to apply to this generic message
 * @param icon the generic icon to display next to the message
 */
@Composable
fun GenericMessage(
    message: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(MarginDouble))
        }
        Text(
            text = message,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@WidgetPreviews
@Composable
private fun PreviewFavoritesMessage() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            val longMessage = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Suspendisse lacinia egestas orci sit amet euismod. Morbi mollis egestas " +
                "dignissim. Sed consequat neque in mauris pretium facilisis."
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MarginDouble),
            ) {
                GenericMessage(
                    message = "Just a message",
                    modifier = Modifier.fillMaxWidth(),
                )
                GenericMessage(
                    message = longMessage,
                    modifier = Modifier.fillMaxWidth(),
                )
                GenericMessage(
                    message = "Just a message",
                    icon = Icons.Default.Warning,
                    modifier = Modifier.fillMaxWidth(),
                )
                GenericMessage(
                    message = longMessage,
                    icon = Icons.Default.Warning,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
