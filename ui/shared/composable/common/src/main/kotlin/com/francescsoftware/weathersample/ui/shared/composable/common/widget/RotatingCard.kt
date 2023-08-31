@file:Suppress("UndocumentedPublicClass", "UndocumentedPublicFunction", "UndocumentedPublicProperty", "MagicNumber")

package com.francescsoftware.weathersample.ui.shared.composable.common.widget

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme

private const val RotationDurationMillis = 400
private const val CameraDistance = 12f
private const val HalfRotationDegrees = 90f
private const val FullRotationDegrees = 180f

enum class CardFace(val angle: Float) {
    Front(0f) {
        override val next: CardFace
            get() = Back
    },
    Back(180f) {
        override val next: CardFace
            get() = Front
    };

    abstract val next: CardFace
}

enum class RotationAxis {
    AxisX,
    AxisY,
}

@Composable
fun RotatingCard(
    cardFace: CardFace,
    onClick: (CardFace) -> Unit,
    modifier: Modifier = Modifier,
    axis: RotationAxis = RotationAxis.AxisY,
    back: @Composable () -> Unit = {},
    front: @Composable () -> Unit = {},
) {
    val rotation = animateFloatAsState(
        targetValue = cardFace.angle,
        animationSpec = tween(
            durationMillis = RotationDurationMillis,
            easing = FastOutSlowInEasing,
        ),
        label = "rotation",
    )
    Card(
        onClick = { onClick(cardFace) },
        modifier = modifier
            .graphicsLayer {
                if (axis == RotationAxis.AxisX) {
                    rotationX = rotation.value
                } else {
                    rotationY = rotation.value
                }
                cameraDistance = CameraDistance * density
            },
    ) {
        if (rotation.value <= HalfRotationDegrees) {
            Box(
                Modifier.fillMaxSize()
            ) {
                front()
            }
        } else {
            Box(
                Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        if (axis == RotationAxis.AxisX) {
                            rotationX = FullRotationDegrees
                        } else {
                            rotationY = FullRotationDegrees
                        }
                    },
            ) {
                back()
            }
        }
    }
}

@Preview(widthDp = 360, showBackground = true)
@Composable
private fun PreviewRotatingCard() {
    WeatherSampleTheme {
        var face: CardFace by remember {
            mutableStateOf(CardFace.Front)
        }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = MarginDouble),
            color = MaterialTheme.colorScheme.background,
        ) {
            RotatingCard(
                cardFace = face,
                onClick = { face = face.next },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                front = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Red),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "Front",
                            style = MaterialTheme.typography.displayMedium,
                            color = Color.White,
                        )
                    }
                },
                back = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Blue),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "Back",
                            style = MaterialTheme.typography.displayMedium,
                            color = Color.White,
                        )
                    }
                },
            )
        }
    }
}
