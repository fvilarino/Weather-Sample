package com.francescsoftware.weathersample.ui.feature.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.francescsoftware.weathersample.domain.preferencesinteractor.api.AppTheme
import com.francescsoftware.weathersample.ui.feature.settings.R
import com.francescsoftware.weathersample.ui.shared.composable.common.widget.EqualSizeTiles
import com.francescsoftware.weathersample.ui.shared.styles.MarginDouble
import com.francescsoftware.weathersample.ui.shared.styles.MarginSingle
import com.francescsoftware.weathersample.ui.shared.styles.WeatherSampleTheme
import com.francescsoftware.weathersample.ui.shared.styles.WidgetPreviews

private const val SelectedItemAlpha = .15f

@Composable
internal fun ThemePreference(
    selectedTheme: AppTheme,
    onClick: (AppTheme) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MarginSingle),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(id = R.string.app_theme),
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
        )
        EqualSizeTiles(
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            AppTheme.entries.forEach { entry ->
                ThemeButton(
                    label = entry.label,
                    selected = entry == selectedTheme,
                    onClick = { onClick(entry) },
                )
            }
        }
    }
}

private val AppTheme.label: String
    @Composable get() = when (this) {
        AppTheme.System -> stringResource(id = R.string.theme_follow_system)
        AppTheme.Light -> stringResource(id = R.string.theme_light)
        AppTheme.Dark -> stringResource(id = R.string.theme_dark)
    }

@Composable
private fun ThemeButton(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .clickable(onClick = onClick)
            .background(
                color = if (selected) {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = SelectedItemAlpha)
                } else {
                    Color.Transparent
                },
                shape = RoundedCornerShape(8.dp),
            )
            .padding(horizontal = MarginDouble, vertical = MarginSingle),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alpha(if (selected) 1f else MediumEmphasisAlpha),
        )
    }
}

@WidgetPreviews
@Composable
private fun PreviewThemePreference() {
    WeatherSampleTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            var selectedTheme: AppTheme by remember {
                mutableStateOf(AppTheme.System)
            }
            ThemePreference(
                selectedTheme = selectedTheme,
                onClick = { selectedTheme = it },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
