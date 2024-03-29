package com.francescsoftware.weathersample.ui.shared.composable.common.overlay

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.window.DialogProperties
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.slack.circuit.overlay.Overlay
import com.slack.circuit.overlay.OverlayNavigator

enum class DialogResult {
    Confirm,
    Cancel,
    Dismiss,
}

@Stable
class DialogOverlay(
    private val confirmButtonText: @Composable () -> Unit,
    private val dismissButtonText: (@Composable () -> Unit)? = null,
    private val title: @Composable (() -> Unit)? = null,
    private val text: @Composable (() -> Unit)? = null,
    private val icon: @Composable (() -> Unit)? = null,
    private val dismissOnBackPress: Boolean = true,
    private val dismissOnClickOutside: Boolean = true,
) : Overlay<DialogResult> {

    @Composable
    override fun Content(navigator: OverlayNavigator<DialogResult>) {
        WeatherSampleTheme {
            AlertDialog(
                onDismissRequest = { navigator.finish(DialogResult.Dismiss) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            navigator.finish(DialogResult.Confirm)
                        },
                    ) { confirmButtonText() }
                },
                dismissButton = dismissButtonText?.let { dismissButtonText ->
                    {
                        TextButton(
                            onClick = {
                                navigator.finish(DialogResult.Cancel)
                            },
                        ) {
                            dismissButtonText()
                        }
                    }
                },
                icon = icon,
                title = title,
                text = text,
                properties = DialogProperties(
                    dismissOnBackPress = dismissOnBackPress,
                    dismissOnClickOutside = dismissOnClickOutside,
                ),
            )
        }
    }
}
