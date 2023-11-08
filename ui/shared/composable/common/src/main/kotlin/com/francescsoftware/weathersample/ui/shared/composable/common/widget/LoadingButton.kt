package com.francescsoftware.weathersample.ui.shared.composable.common.widget

import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginHalf
import com.francescsoftware.weathersample.ui.shared.styles.MarginSingle
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

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
private const val IndicatorSize = 12f
private const val MinIndicatorAlpha = .2f
private const val BounceAnimationDurationMillis = 300
private const val FadeAnimationDurationMillis = 600

/**
 * A button that displays a loading indicator when busy.
 *
 * @param onClick called when the button is clicked
 * @param modifier the [Modifier] to apply to this button
 * @param enabled if true the button is enabled, if false it is disabled
 * @param loading if true the loading indicator will display, otherwise the content will display
 * @param animationType [AnimationType] to use for the loading state
 * @param colors [ButtonColors] to to use for the button
 * @param indicatorSpacing space, in [Dp], between the loading indicators
 * @param content the button content when not animating
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
                modifier = Modifier.graphicsLayer { alpha = contentAlpha },
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
        AnimationType.Fade,
        -> tween(durationMillis = animationDuration)

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
        AnimationType.LazyBounce,
        -> BounceAnimationDurationMillis

        AnimationType.Fade -> FadeAnimationDurationMillis
    }

private val AnimationType.animationDelay: Int
    get() = animationDuration / NumIndicators

private val AnimationType.initialValue: Float
    get() = when (this) {
        AnimationType.Bounce,
        AnimationType.LazyBounce,
        -> -IndicatorSize

        AnimationType.Fade -> 1f
    }

private val AnimationType.targetValue: Float
    get() = when (this) {
        AnimationType.Bounce,
        AnimationType.LazyBounce,
        -> IndicatorSize

        AnimationType.Fade -> MinIndicatorAlpha
    }

/** Loading indicator state */
@Stable
interface LoadingIndicatorState {
    /**
     * The animation value for an animated dot.
     *
     * @param index the index of the animated dot to get the animated value from
     * @return the animated value for the dot at [index] position
     */
    operator fun get(index: Int): Float

    /** Starts the loading indicator animation. */
    suspend fun start()
}

internal class LoadingIndicatorStateImpl(
    private val animationType: AnimationType,
) : LoadingIndicatorState {
    private val animatedValues = List(NumIndicators) { mutableFloatStateOf(0f) }

    override fun get(index: Int): Float = animatedValues[index].floatValue

    override suspend fun start() {
        coroutineScope {
            repeat(NumIndicators) { index ->
                launch {
                    animate(
                        initialValue = animationType.initialValue,
                        targetValue = animationType.targetValue,
                        animationSpec = infiniteRepeatable(
                            animation = animationType.animationSpec,
                            repeatMode = RepeatMode.Reverse,
                            initialStartOffset = StartOffset(animationType.animationDelay * index),
                        ),
                    ) { value, _ -> animatedValues[index].floatValue = value }
                }
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoadingIndicatorStateImpl

        return animatedValues == other.animatedValues
    }

    override fun hashCode(): Int = animatedValues.hashCode()

    companion object {
        val Saver = Saver<LoadingIndicatorStateImpl, List<*>>(
            save = { stateHolder ->
                listOf(
                    stateHolder.animationType,
                )
            },
            restore = { args ->
                LoadingIndicatorStateImpl(
                    animationType = args[0] as AnimationType,
                )
            },
        )
    }
}

/**
 * Factory method for the [LoadingButton] [LoadingIndicator]
 *
 * @param animationType [AnimationType] for the indicator animation.
 * @return an instance of [LoadingIndicatorState]
 */
@Composable
fun rememberLoadingIndicatorState(
    animationType: AnimationType,
): LoadingIndicatorState = rememberSaveable(
    saver = LoadingIndicatorStateImpl.Saver,
) {
    LoadingIndicatorStateImpl(animationType = animationType)
}

@Composable
fun LoadingIndicator(
    animating: Boolean,
    animationType: AnimationType,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    indicatorSpacing: Dp = MarginHalf,
) {
    val state = rememberLoadingIndicatorState(animationType)
    LaunchedEffect(key1 = animating, key2 = animationType) {
        if (animating) {
            state.start()
        }
    }
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
                            AnimationType.LazyBounce,
                            -> Modifier
                                .offset {
                                    IntOffset(
                                        x = 0,
                                        y = state[index]
                                            .coerceAtMost(IndicatorSize)
                                            .roundToInt(),
                                    )
                                }

                            AnimationType.Fade -> Modifier.graphicsLayer { alpha = state[index] }
                        },
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
            .background(color = color),
    )
}

@WidgetPreviews
@Composable
private fun PreviewLoadingButton() {
    WeatherSampleTheme {
        var loading1 by rememberSaveable {
            mutableStateOf(false)
        }
        var loading2 by rememberSaveable {
            mutableStateOf(false)
        }
        var loading3 by rememberSaveable {
            mutableStateOf(false)
        }
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                LoadingButton(
                    onClick = { loading1 = !loading1 },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = MarginDouble),
                    loading = loading1,
                ) {
                    Text(
                        text = "Refresh",
                    )
                }
                LoadingButton(
                    onClick = { loading2 = !loading2 },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = MarginDouble),
                    animationType = AnimationType.LazyBounce,
                    loading = loading2,
                ) {
                    Text(
                        text = "Refresh",
                    )
                }
                LoadingButton(
                    onClick = { loading3 = !loading3 },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = MarginDouble),
                    animationType = AnimationType.Fade,
                    loading = loading3,
                ) {
                    Text(
                        text = "Refresh",
                    )
                }
            }
        }
    }
}
