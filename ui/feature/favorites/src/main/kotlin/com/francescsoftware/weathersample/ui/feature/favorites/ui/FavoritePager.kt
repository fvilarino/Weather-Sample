package com.francescsoftware.weathersample.ui.feature.favorites.ui

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.PagerIndicator
import com.francescsoftware.weathersample.ui.shared.deviceclass.DeviceClass
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginSingle
import com.francescsoftware.weathersample.ui.shared.styles.PhonePreviews
import com.francescsoftware.weathersample.ui.shared.styles.TabletPreviews
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import kotlinx.collections.immutable.persistentListOf
import kotlin.math.absoluteValue

private val PageWidth = 420.dp
private const val MinPageAlpha = .5f
private const val MinPageScale = .85f

@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun FavoritePager(
    state: FavoritePagerState,
    deviceClass: DeviceClass,
    onDeleteClick: (City) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = MarginDouble),
) {
    val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
    val pageSize = if (deviceClass == DeviceClass.Compact && isPortrait) {
        PageSize.Fill
    } else {
        PageSize.Fixed(PageWidth)
    }
    Box(
        modifier = modifier,
    ) {
        val pagerState = rememberPagerState(
            pageCount = { state.pages.size },
        )
        HorizontalPager(
            pageSize = pageSize,
            pageSpacing = MarginDouble,
            contentPadding = contentPadding,
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { index ->
            FavoritePage(
                state = state.pages[index],
                onDeleteClick = onDeleteClick,
                modifier = Modifier
                    .fillMaxSize()
                    .pagerEffect(
                        pagerState = pagerState,
                        index = index,
                        deviceClass = deviceClass,
                    ),
            )
        }
        if (state.pages.size > 1) {
            PagerIndicator(
                pagerState = pagerState,
                itemCount = state.pages.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.surface,
                            ),
                        ),
                    )
                    .padding(vertical = MarginSingle)
                    .align(Alignment.BottomCenter),
            )
        }
    }
}

private fun Modifier.pagerEffect(
    pagerState: PagerState,
    index: Int,
    deviceClass: DeviceClass,
) = then(
    if (deviceClass == DeviceClass.Compact) {
        Modifier.graphicsLayer {
            val pageOffset = ((pagerState.currentPage - index) + pagerState.currentPageOffsetFraction)
                .absoluteValue
            alpha = lerp(
                start = MinPageAlpha,
                stop = 1f,
                fraction = 1f - pageOffset.coerceIn(0f, 1f),
            )
            val scale = lerp(
                start = MinPageScale,
                stop = 1f,
                fraction = 1f - pageOffset.coerceIn(0f, 1f),
            )
            scaleX = scale
            scaleY = scale
        }
    } else {
        Modifier
    },
)

@PhonePreviews
@Composable
private fun PreviewPhoneFavoriteCard() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            FavoritePager(
                state = FavoritePagerState(
                    pages = persistentListOf(
                        VancouverFavoriteCardState,
                        BarcelonaFavoriteCardState,
                        LondonFavoriteCardState,
                    ),
                ),
                deviceClass = DeviceClass.Compact,
                onDeleteClick = { },
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@TabletPreviews
@Composable
private fun PreviewTabletFavoriteCard() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            FavoritePager(
                state = FavoritePagerState(
                    pages = persistentListOf(
                        VancouverFavoriteCardState,
                        BarcelonaFavoriteCardState,
                        LondonFavoriteCardState,
                    ),
                ),
                deviceClass = DeviceClass.Expanded,
                onDeleteClick = { },
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
