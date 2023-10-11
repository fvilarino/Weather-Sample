package com.francescsoftware.weathersample.ui.shared.composable.common.widget

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Crossfade variant that takes a lambda to determine when to fade the content
 *
 * @param targetState The key representing the target layout state
 * @param contentKey A lambda providing a key based on the target state. Every time this key changes the animation will
 *     trigger
 * @param modifier Modifier to be applied to the animation container.
 * @param animationSpec the [AnimationSpec] to configure the animation.
 * @param label An optional label to differentiate from other animations in Android Studio.
 * @param content The composable content to render
 */
@Composable
fun <T : Any> Crossfade(
    targetState: T,
    contentKey: (T) -> Any,
    modifier: Modifier = Modifier,
    animationSpec: FiniteAnimationSpec<Float> = tween(),
    label: String = "Crossfade",
    content: @Composable (T) -> Unit,
) {
    val transition = updateTransition(targetState, label)
    transition.Crossfade(
        modifier,
        animationSpec,
        contentKey = contentKey,
        content = content,
    )
}
