package com.francescsoftware.weathersample.ui.shared.composable.common

import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginHalf
import com.francescsoftware.weathersample.ui.shared.styles.MarginSingle
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Animation type
 *
 * @constructor Create empty Animation type
 */
enum class AnimationType {
    /**
     * Bounce
     *
     * @constructor Create empty Bounce
     */
    Bounce,

    /**
     * Lazy bounce
     *
     * @constructor Create empty Lazy bounce
     */
    LazyBounce,

    /**
     * Fade
     *
     * @constructor Create empty Fade
     */
    Fade,
}

private const val NumIndicators = 3
private const val IndicatorSize = 12
private const val MinIndicatorAlpha = .2f
private const val BounceAnimationDurationMillis = 300
private const val FadeAnimationDurationMillis = 600

/**
 * A button that displays a loading indicator when busy.
 *
 * @param onClick - called when the button is clicked
 * @param modifier - the [Modifier] to apply to this button
 * @param enabled - if true the button is enabled, if false it is disabled
 * @param loading - if true the loading indicator will display, otherwise the content will display
 * @param animationType - [AnimationType] to use for the loading state
 * @param colors - [ButtonColors] to to use for the button
 * @param indicatorSpacing - space, in [Dp], between the loading indicators
 * @param content - the button content when not animating
 */
@Composable
fun LoadingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    animationType: AnimationType = AnimationType.Bounce,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    indicatorSpacing: Dp = MarginSingle,
    content: @Composable () -> Unit,
) {
    val contentAlpha by animateFloatAsState(
        targetValue = if (loading) 0f else 1f,
        label = "content",
    )
    val loadingAlpha by animateFloatAsState(
        targetValue = if (loading) 1f else 0f,
        label = "loading",
    )
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            LoadingIndicator(
                animating = loading,
                animationType = animationType,
                modifier = Modifier.graphicsLayer { alpha = loadingAlpha },
                indicatorSpacing = indicatorSpacing,
            )
            Box(
                modifier = Modifier.graphicsLayer { alpha = contentAlpha }
            ) {
                content()
            }
        }
    }
}

@Suppress("MagicNumber")
private val AnimationType.animationSpec: DurationBasedAnimationSpec<Float>
    get() = when (this) {
        AnimationType.Bounce,
        AnimationType.Fade -> tween(durationMillis = animationDuration)

        AnimationType.LazyBounce -> keyframes {
            durationMillis = animationDuration
            initialValue at 0
            0f at animationDuration / 4
            targetValue / 2f at animationDuration / 2
            targetValue / 2f at animationDuration
        }
    }

private val AnimationType.animationDuration: Int
    get() = when (this) {
        AnimationType.Bounce,
        AnimationType.LazyBounce -> BounceAnimationDurationMillis

        AnimationType.Fade -> FadeAnimationDurationMillis
    }

private val AnimationType.animationDelay: Int
    get() = animationDuration / NumIndicators

private val AnimationType.initialValue: Float
    get() = when (this) {
        AnimationType.Bounce -> IndicatorSize / 2f
        AnimationType.LazyBounce -> -IndicatorSize / 2f
        AnimationType.Fade -> 1f
    }

private val AnimationType.targetValue: Float
    get() = when (this) {
        AnimationType.Bounce -> -IndicatorSize / 2f
        AnimationType.LazyBounce -> IndicatorSize / 2f
        AnimationType.Fade -> MinIndicatorAlpha
    }

/** Loading indicator state */
@Stable
interface LoadingIndicatorState {
    /**
     * The animation value for an animated dot.
     *
     * @param index - the index of the animated dot to get the animated value from
     * @return the animated value for the dot at [index] position
     */
    operator fun get(index: Int): Float

    /**
     * Starts the loading indicator animation.
     *
     * @param animationType - [AnimationType] to use for the loading button
     * @param scope - the [CoroutineScope] to launch the coroutines on
     */
    fun start(animationType: AnimationType, scope: CoroutineScope)
}

internal class LoadingIndicatorStateImpl : LoadingIndicatorState {
    private val animatedValues = List(NumIndicators) { mutableStateOf(0f) }

    override fun get(index: Int): Float = animatedValues[index].value

    override fun start(animationType: AnimationType, scope: CoroutineScope) {
        repeat(NumIndicators) { index ->
            scope.launch {
                animate(
                    initialValue = animationType.initialValue,
                    targetValue = animationType.targetValue,
                    animationSpec = infiniteRepeatable(
                        animation = animationType.animationSpec,
                        repeatMode = RepeatMode.Reverse,
                        initialStartOffset = StartOffset(animationType.animationDelay * index)
                    ),
                ) { value, _ -> animatedValues[index].value = value }
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoadingIndicatorStateImpl

        if (animatedValues != other.animatedValues) return false

        return true
    }

    override fun hashCode(): Int {
        return animatedValues.hashCode()
    }
}

/**
 * Factory method for the [LoadingButton] [LoadingIndicator]
 *
 * @param animating - if true the indicator animates, if false it does not
 * @param animationType - [AnimationType] for the indicator animation.
 * @return an instance of [LoadingIndicatorState]
 */
@Composable
fun rememberLoadingIndicatorState(
    animating: Boolean,
    animationType: AnimationType,
): LoadingIndicatorState {
    val state = remember {
        LoadingIndicatorStateImpl()
    }
    LaunchedEffect(key1 = Unit) {
        if (animating) {
            state.start(animationType, this)
        }
    }
    return state
}

@Composable
private fun LoadingIndicator(
    animating: Boolean,
    animationType: AnimationType,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    indicatorSpacing: Dp = MarginHalf,
) {
    val state = rememberLoadingIndicatorState(animating, animationType)
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(NumIndicators) { index ->
            LoadingDot(
                modifier = Modifier
                    .padding(horizontal = indicatorSpacing)
                    .width(IndicatorSize.dp)
                    .aspectRatio(1f)
                    .then(
                        when (animationType) {
                            AnimationType.Bounce,
                            AnimationType.LazyBounce -> Modifier.offset(
                                y = state[index].coerceAtMost(
                                    IndicatorSize / 2f
                                ).dp
                            )

                            AnimationType.Fade -> Modifier.graphicsLayer { alpha = state[index] }
                        }
                    ),
                color = color,
            )
        }
    }
}

@Composable
private fun LoadingDot(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(color = color)
    )
}

@WidgetPreviews
@Composable
private fun PreviewLoadingButton() {
    WeatherSampleTheme {
        var loading by remember {
            mutableStateOf(false)
        }
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            LoadingButton(
                onClick = { loading = !loading },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = MarginDouble),
                loading = loading,
            ) {
                Text(
                    text = "Refresh"
                )
            }
        }
    }
}