@file:Suppress("MatchingDeclarationName")

package com.francescsoftware.weathersample.ui.shared.composable.common.widget

import android.content.res.Configuration
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.ui.shared.styles.BorderWidth
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginSingle
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme

/** Identifier for selected option in [TwoOptionsSelector] */
enum class TwoOptionsSelectorOptions {
    Left,
    Right,
}

private const val SelectorTransitionDurationMillis = 300

/**
 * Horizontal selector with 2 mutually exclusive options for the user to choose from.
 *
 * @param leftLabel - label for the left side option
 * @param rightLabel - label for the right side option
 * @param selectedOption - indicates the currently selected option, one of [TwoOptionsSelectorOptions]
 * @param onOptionSelect - called when the user clicks on an option
 * @param modifier - the [Modifier] to apply to this selector
 * @param selectedColor - the color to use for the background of the selected option
 * @param deselectedColor - the color to use for the background of the unselected option
 */
@Composable
fun TwoOptionsSelector(
    leftLabel: String,
    rightLabel: String,
    selectedOption: TwoOptionsSelectorOptions,
    onOptionSelect: (TwoOptionsSelectorOptions) -> Unit,
    modifier: Modifier = Modifier,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    deselectedColor: Color = MaterialTheme.colorScheme.surface,
) {
    val transitionData = twoOptionsSelectorTransitionData(
        selectedOption = selectedOption,
        selectedColor = selectedColor,
        deselectedColor = deselectedColor,
    )
    EqualSizeTiles(
        modifier = modifier
            .selectableGroup()
            .clip(
                RoundedCornerShape(
                    topStartPercent = 50,
                    bottomStartPercent = 50,
                    topEndPercent = 50,
                    bottomEndPercent = 50,
                ),
            )
            .border(
                width = BorderWidth,
                color = selectedColor,
                shape = RoundedCornerShape(
                    topStartPercent = 50,
                    bottomStartPercent = 50,
                    topEndPercent = 50,
                    bottomEndPercent = 50,
                ),
            ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(transitionData.leftBackgroundColor)
                .selectable(
                    selected = selectedOption == TwoOptionsSelectorOptions.Left,
                    onClick = { onOptionSelect(TwoOptionsSelectorOptions.Left) },
                    role = Role.RadioButton,
                )
                .padding(vertical = MarginSingle, horizontal = MarginDouble),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = leftLabel,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = transitionData.leftTextColor,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .selectable(
                    selected = selectedOption == TwoOptionsSelectorOptions.Right,
                    onClick = { onOptionSelect(TwoOptionsSelectorOptions.Right) },
                    role = Role.RadioButton,
                )
                .background(transitionData.rightBackgroundColor)
                .padding(vertical = MarginSingle, horizontal = MarginDouble),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = rightLabel,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = transitionData.rightTextColor,
            )
        }
    }
}

private class TwoOptionsSelectorTransitionData(
    leftBackgroundColor: State<Color>,
    rightBackgroundColor: State<Color>,
    leftTextColor: State<Color>,
    rightTextColor: State<Color>,
) {
    val leftBackgroundColor by leftBackgroundColor
    val rightBackgroundColor by rightBackgroundColor
    val leftTextColor by leftTextColor
    val rightTextColor by rightTextColor
}

@Composable
private fun twoOptionsSelectorTransitionData(
    selectedOption: TwoOptionsSelectorOptions,
    selectedColor: Color,
    deselectedColor: Color,
): TwoOptionsSelectorTransitionData {
    val transition = updateTransition(selectedOption, label = "selectorColor")
    val leftBackgroundColor = transition.animateColor(
        transitionSpec = { tween(durationMillis = SelectorTransitionDurationMillis) },
        label = "leftColorSpec",
    ) { state ->
        when (state) {
            TwoOptionsSelectorOptions.Left -> selectedColor
            TwoOptionsSelectorOptions.Right -> deselectedColor
        }
    }
    val rightTextColor = transition.animateColor(
        transitionSpec = { tween(durationMillis = SelectorTransitionDurationMillis) },
        label = "leftTextColorSpec",
    ) { state ->
        when (state) {
            TwoOptionsSelectorOptions.Left -> contentColorFor(backgroundColor = deselectedColor)
            TwoOptionsSelectorOptions.Right -> contentColorFor(backgroundColor = selectedColor)
        }
    }
    val rightBackgroundColor = transition.animateColor(
        transitionSpec = { tween(durationMillis = SelectorTransitionDurationMillis) },
        label = "rightColorSpec",
    ) { state ->
        when (state) {
            TwoOptionsSelectorOptions.Left -> deselectedColor
            TwoOptionsSelectorOptions.Right -> selectedColor
        }
    }
    val leftTextColor = transition.animateColor(
        transitionSpec = { tween(durationMillis = SelectorTransitionDurationMillis) },
        label = "rightTextColorSpec",
    ) { state ->
        when (state) {
            TwoOptionsSelectorOptions.Left -> contentColorFor(backgroundColor = selectedColor)
            TwoOptionsSelectorOptions.Right -> contentColorFor(backgroundColor = deselectedColor)
        }
    }
    return remember(selectedOption) {
        TwoOptionsSelectorTransitionData(
            leftBackgroundColor = leftBackgroundColor,
            rightBackgroundColor = rightBackgroundColor,
            leftTextColor = leftTextColor,
            rightTextColor = rightTextColor,
        )
    }
}

@Preview(widthDp = 360, heightDp = 80)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, widthDp = 360, heightDp = 80)
@Composable
private fun PreviewTwoOptionsSelector() {
    WeatherSampleTheme {
        var option by remember {
            mutableStateOf(TwoOptionsSelectorOptions.Left)
        }
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            TwoOptionsSelector(
                leftLabel = "Left",
                rightLabel = "Right Label",
                selectedOption = option,
                onOptionSelect = { selected ->
                    option = selected
                },
                modifier = Modifier
                    .padding(vertical = MarginDouble)
                    .height(40.dp),
            )
        }
    }
}
