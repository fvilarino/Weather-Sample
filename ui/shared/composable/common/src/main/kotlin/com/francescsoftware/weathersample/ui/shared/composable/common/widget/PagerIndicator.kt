package com.francescsoftware.weathersample.ui.shared.composable.common.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginSingle
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import kotlin.math.roundToInt

private val IndicatorSize = 16.dp
private const val NumIndicators = 5
private const val EdgeItemScale = .5f
private const val IntermediateItemScale = .8f

/**
 * An indicator for a Pager composable
 *
 * @param pagerState the [PagerState] for the pager this indicator is associated with
 * @param itemCount the number of items to display in the pager
 * @param modifier the [Modifier] to apply to this indicator
 * @param indicatorCount the number of indicator dots to display (should be an odd number)
 * @param indicatorSize the size of each indicator dot
 * @param indicatorShape the shape of the indicator dot
 * @param space the space, in [Dp], between indicators
 * @param activeColor the [Color] for the active indicator dot
 * @param inActiveColor the [Color] for the inactive indicator dots
 * @param onClick the click lambda for the indicator
 */
@Composable
fun PagerIndicator(
    pagerState: PagerState,
    itemCount: Int,
    modifier: Modifier = Modifier,
    indicatorCount: Int = NumIndicators,
    indicatorSize: Dp = IndicatorSize,
    indicatorShape: Shape = CircleShape,
    space: Dp = MarginSingle,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    inActiveColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = .4f),
    onClick: ((Int) -> Unit)? = null,
) {
    val listState = rememberLazyListState()
    val visibleItems = indicatorCount.coerceAtMost(itemCount)
    val totalWidth = indicatorSize * visibleItems + space * (visibleItems - 1)
    val widthInPx = with(LocalDensity.current) { indicatorSize.toPx() }

    LaunchedEffect(key1 = pagerState.currentPage, key2 = itemCount) {
        val viewportSize = listState.layoutInfo.viewportSize
        listState.animateScrollToItem(
            index = pagerState.currentPage,
            scrollOffset = (widthInPx / 2 - viewportSize.width / 2).roundToInt(),
        )
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        LazyRow(
            state = listState,
            modifier = Modifier.width(totalWidth),
            contentPadding = PaddingValues(vertical = space),
            horizontalArrangement = Arrangement.spacedBy(space),
            userScrollEnabled = false,
        ) {
            items(itemCount) { index ->
                val isSelected = (index == pagerState.currentPage)
                val isRightEdgeItem = pagerState.isRightEdge(
                    itemCount = itemCount,
                    index = index,
                    indicatorCount = indicatorCount,
                )
                val isLeftEdgeItem = pagerState.isLeftEdge(
                    itemCount = itemCount,
                    index = index,
                    indicatorCount = indicatorCount,
                )
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            val scale = if (isSelected) {
                                1f
                            } else if (isLeftEdgeItem || isRightEdgeItem) {
                                EdgeItemScale
                            } else {
                                IntermediateItemScale
                            }
                            scaleX = scale
                            scaleY = scale
                        }
                        .clip(indicatorShape)
                        .size(indicatorSize)
                        .background(
                            color = if (isSelected) activeColor else inActiveColor,
                            shape = indicatorShape,
                        )
                        .then(
                            if (onClick != null) {
                                Modifier.clickable {
                                    onClick(index)
                                }
                            } else {
                                Modifier
                            }
                        )
                )
            }
        }
    }
}

private fun PagerState.isLeftEdge(
    itemCount: Int,
    index: Int,
    indicatorCount: Int,
): Boolean {
    val centerItemIndex = indicatorCount / 2
    return index <= currentPage - centerItemIndex &&
        currentPage > centerItemIndex &&
        index < itemCount - indicatorCount + 1
}

private fun PagerState.isRightEdge(
    itemCount: Int,
    index: Int,
    indicatorCount: Int,
): Boolean {
    val centerItemIndex = indicatorCount / 2
    val rightEdge = currentPage < centerItemIndex &&
        index >= indicatorCount - 1
    val rightEdgeOverflow = currentPage >= centerItemIndex &&
        index >= currentPage + centerItemIndex &&
        index <= itemCount - centerItemIndex + 1
    return rightEdge || rightEdgeOverflow
}

@Preview(widthDp = 360, heightDp = 480, showBackground = true)
@Composable
private fun PreviewPagerIndicator() {
    WeatherSampleTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = MarginDouble),
            color = MaterialTheme.colorScheme.background,
        ) {
            val pages = remember {
                List(10) { "Page #$it" }
            }
            val pagerState = rememberPagerState {
                pages.size
            }
            Column {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = pages[it])
                    }
                }
                PagerIndicator(
                    pagerState = pagerState,
                    itemCount = pages.size,
                    modifier = Modifier
                        .padding(all = MarginDouble)
                        .align(Alignment.CenterHorizontally),
                )
            }
        }
    }
}
