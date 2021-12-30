package com.francescsoftware.weathersample.presentation.shared.widget

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.styles.MarginHalf
import com.francescsoftware.weathersample.styles.MarginSingle
import com.francescsoftware.weathersample.styles.WeatherSampleTheme
import kotlinx.coroutines.delay

enum class AnimationType {
    Bounce,
    Fade,
}

private const val NumIndicators = 3
private const val IndicatorSize = 12
private const val AnimationDurationMillis = 300
private const val AnimationDelayMillis = AnimationDurationMillis / NumIndicators

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
    val contentAlpha by animateFloatAsState(targetValue = if (loading) 0f else 1f)
    val loadingAlpha by animateFloatAsState(targetValue = if (loading) 1f else 0f)
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
                modifier = Modifier.graphicsLayer { alpha = loadingAlpha },
                color = colors.contentColor(enabled = enabled).value,
                indicatorSpacing = indicatorSpacing,
                animationType = animationType,
            )
            Box(
                modifier = Modifier.graphicsLayer { alpha = contentAlpha }
            ) {
                content()
            }
        }
    }
}

private val AnimationType.animationDuration: Int
    get() = when (this) {
        AnimationType.Bounce -> 300
        AnimationType.Fade -> 600
    }

private val AnimationType.animationDelay: Int
    get() = animationDuration / NumIndicators

private val AnimationType.initialValue: Float
    get() = when (this) {
        AnimationType.Bounce -> IndicatorSize / 2f
        AnimationType.Fade -> 1f
    }

private val AnimationType.targetValue: Float
    get() = when (this) {
        AnimationType.Bounce -> -IndicatorSize / 2f
        AnimationType.Fade -> .2f
    }

@Composable
private fun LoadingIndicator(
    animating: Boolean,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    indicatorSpacing: Dp = MarginHalf,
    animationType: AnimationType,
) {
    val animatedValues = List(NumIndicators) { index ->
        var animatedValue by remember(key1 = animating, key2 = animationType) { mutableStateOf(0f) }
        LaunchedEffect(key1 = animating, key2 = animationType) {
            if (animating) {
                delay(animationType.animationDelay.toLong() * index)
                animate(
                    initialValue = animationType.initialValue,
                    targetValue = animationType.targetValue,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = animationType.animationDuration),
                        repeatMode = RepeatMode.Reverse,
                    ),
                ) { value, _ -> animatedValue = value }
            }
        }
        animatedValue
    }
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        animatedValues.forEach { animatedValue ->
            LoadingDot(
                modifier = Modifier
                    .padding(horizontal = indicatorSpacing)
                    .width(IndicatorSize.dp)
                    .aspectRatio(1f)
                    .then(
                        when (animationType) {
                            AnimationType.Bounce -> Modifier.offset(y = animatedValue.dp)
                            AnimationType.Fade -> Modifier.graphicsLayer { alpha = animatedValue }
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

@Preview(widthDp = 360, heightDp = 360)
@Composable
private fun PreviewLoadingButton() {
    WeatherSampleTheme {
        var loading by remember {
            mutableStateOf(false)
        }
        Surface(modifier = Modifier.fillMaxSize()) {
            LoadingButton(
                onClick = { loading = !loading },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                loading = loading,
            ) {
                Text(
                    text = "Refresh"
                )
            }
        }
    }
}

@Preview(widthDp = 360, heightDp = 360)
@Composable
private fun PreviewLoadingIndicator() {
    WeatherSampleTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            LoadingIndicator(
                animating = true,
                modifier = Modifier
                    .height(64.dp)
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                animationType = AnimationType.Bounce,
            )
        }
    }
}