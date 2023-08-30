@file:Suppress("MatchingDeclarationName")

package com.francescsoftware.weathersample.ui.shared.composable.common.widget

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import com.francescsoftware.weathersample.ui.shared.composable.common.R
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews

/** Animation type for the favorite toggle */
enum class FavoriteAnimation {
    Scale,
    RotateY,
    RotateX,
}

private const val SwapThreshold = .5f
private const val HalfCircleDegrees = 180f

/**
 * A toggleable favorite button
 *
 * @param isFavorite whether the item is favorited
 * @param onClick a lambda called when clicking on this toggle
 * @param modifier the [Modifier] to apply to the favorite toggle
 * @param animationType the [FavoriteAnimation] to apply when toggling the value
 * @param color the [Color] for the favorite icon
 */
@Composable
fun FavoriteToggle(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    animationType: FavoriteAnimation = FavoriteAnimation.Scale,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    val animation by animateFloatAsState(
        targetValue = if (isFavorite) 1f else 0f,
        label = "favorite_animation",
        animationSpec = when (animationType) {
            FavoriteAnimation.Scale -> spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMediumLow,
            )

            FavoriteAnimation.RotateX,
            FavoriteAnimation.RotateY -> tween()
        }
    )
    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
            )
            .graphicsLayer {
                when (animationType) {
                    FavoriteAnimation.Scale -> {
                        val scale = if (animation <= SwapThreshold) {
                            (SwapThreshold - animation) * 2f
                        } else {
                            (animation - SwapThreshold) * 2f
                        }
                        scaleX = scale
                        scaleY = scale
                    }

                    FavoriteAnimation.RotateX -> rotationX = HalfCircleDegrees * animation
                    FavoriteAnimation.RotateY -> rotationY = HalfCircleDegrees * animation
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            if (animation >= SwapThreshold) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            tint = color,
            modifier = when (animationType) {
                FavoriteAnimation.Scale,
                FavoriteAnimation.RotateY -> Modifier

                FavoriteAnimation.RotateX -> Modifier.graphicsLayer {
                    rotationX = if (animation >= SwapThreshold) {
                        HalfCircleDegrees
                    } else {
                        0f
                    }
                }
            },
            contentDescription = stringResource(
                if (isFavorite) {
                    R.string.content_description_remove_favorite
                } else {
                    R.string.content_description_make_favorite
                }
            )
        )
    }
}

@WidgetPreviews
@Composable
private fun PreviewFavoriteToggle() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            var scaleFavorite by remember {
                mutableStateOf(false)
            }
            var rotationXFavorite by remember {
                mutableStateOf(false)
            }
            var rotationYFavorite by remember {
                mutableStateOf(false)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                FavoriteToggle(
                    isFavorite = scaleFavorite,
                    onClick = { scaleFavorite = !scaleFavorite },
                    animationType = FavoriteAnimation.Scale,
                )
                FavoriteToggle(
                    isFavorite = rotationXFavorite,
                    onClick = { rotationXFavorite = !rotationXFavorite },
                    animationType = FavoriteAnimation.RotateX,
                )
                FavoriteToggle(
                    isFavorite = rotationYFavorite,
                    onClick = { rotationYFavorite = !rotationYFavorite },
                    animationType = FavoriteAnimation.RotateY,
                )
            }
        }
    }
}
