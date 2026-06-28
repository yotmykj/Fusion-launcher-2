package com.fusion.launcher.ui.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import com.fusion.launcher.ui.theme.FusionBlue
import com.fusion.launcher.ui.theme.FusionBlueLight
import com.fusion.launcher.ui.theme.FusionSurface
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * The horizontal top bar displayed above the app grid.
 *
 * Contains:
 *  - Live clock (updates every 30 seconds)
 *  - [SearchButton] — opens the search overlay
 *  - [SettingsButton] — opens the system Settings app
 *
 * All interactive elements are individually focusable so D-pad navigation
 * can reach them.
 */
@Composable
fun TopBar(
    onSearchClick:   () -> Unit,
    onSettingsClick: () -> Unit,
    modifier:        Modifier = Modifier,
) {
    Row(
        modifier            = modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp, vertical = 20.dp),
        verticalAlignment   = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        // ── Live clock ──────────────────────────────────────────────────────
        LiveClock()

        // ── Action buttons ───────────────────────────────────────────────────
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            TopBarButton(
                icon  = Icons.Filled.Search,
                label = "Search",
                onClick = onSearchClick,
            )
            TopBarButton(
                icon  = Icons.Filled.Settings,
                label = "Settings",
                onClick = onSettingsClick,
            )
        }
    }
}

// ── Live clock ────────────────────────────────────────────────────────────────

@Composable
private fun LiveClock() {
    val formatter = remember { DateTimeFormatter.ofPattern("HH:mm") }
    var timeText by remember { mutableStateOf(LocalTime.now().format(formatter)) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(30_000L) // update every 30 seconds — no need for per-second precision
            timeText = LocalTime.now().format(formatter)
        }
    }

    Text(
        text  = timeText,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onBackground,
    )
}

// ── Reusable icon button ───────────────────────────────────────────────────────

@Composable
private fun TopBarButton(
    icon:    androidx.compose.ui.graphics.vector.ImageVector,
    label:   String,
    onClick: () -> Unit,
) {
    var focused by remember { mutableStateOf(false) }

    Surface(
        onClick  = onClick,
        modifier = Modifier
            .onFocusChanged { focused = it.isFocused }
            .focusable(),
        shape    = RoundedCornerShape(8.dp),
        color    = if (focused) FusionBlue else FusionSurface,
        shadowElevation = if (focused) 8.dp else 0.dp,
    ) {
        Row(
            modifier            = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment   = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                imageVector        = icon,
                contentDescription = label,
                tint               = if (focused) MaterialTheme.colorScheme.onPrimary
                                     else FusionBlueLight,
                modifier           = Modifier.size(20.dp),
            )
            Text(
                text  = label,
                style = MaterialTheme.typography.labelLarge,
                color = if (focused) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
