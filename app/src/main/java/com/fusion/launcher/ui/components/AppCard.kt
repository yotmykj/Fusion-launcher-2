package com.fusion.launcher.ui.components

import android.graphics.drawable.Drawable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fusion.launcher.ui.theme.FusionBlue
import com.fusion.launcher.ui.theme.FusionBlueLight
import com.fusion.launcher.ui.theme.FusionSurface
import com.fusion.launcher.ui.theme.FusionSurface2

/**
 * A focusable app tile designed for D-pad navigation on Android TV.
 *
 * Visual behaviour:
 *  - Resting: dark surface card, no border.
 *  - Focused:  scales up to 110 %, adds a blue border glow, elevation lifts.
 *
 * The scale animation uses a [spring] so it feels physical rather than
 * mechanical when scrolling quickly through a grid with the D-pad.
 *
 * @param label        App display name shown below the icon.
 * @param icon         App icon [Drawable] rendered via [AppIconImage].
 * @param isFavorite   Shows a star badge when true.
 * @param onClick      Called when the user presses D-pad center / OK.
 * @param onLongClick  Called on long-press — used to toggle favorites.
 * @param modifier     Optional modifier forwarded to the root composable.
 */
@Composable
fun AppCard(
    label:       String,
    icon:        Drawable,
    isFavorite:  Boolean,
    onClick:     () -> Unit,
    onLongClick: () -> Unit,
    modifier:    Modifier = Modifier,
) {
    var focused by remember { mutableStateOf(false) }

    // Spring-based scale — overshoots slightly for a lively feel
    val scale by animateFloatAsState(
        targetValue = if (focused) 1.10f else 1.00f,
        animationSpec = spring(
            dampingRatio = 0.6f,
            stiffness    = 400f,
        ),
        label = "cardScale",
    )

    Surface(
        onClick    = onClick,
        modifier   = modifier
            .scale(scale)
            .onFocusChanged { focused = it.isFocused }
            .focusable(),
        shape      = RoundedCornerShape(12.dp),
        color      = if (focused) FusionSurface2 else FusionSurface,
        shadowElevation = if (focused) 12.dp else 2.dp,
        border = if (focused)
            BorderStroke(width = 2.dp, color = FusionBlueLight)
        else
            BorderStroke(width = 1.dp, color = Color.Transparent),
    ) {
        Column(
            modifier            = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // Icon + optional favorite badge
            Box(contentAlignment = Alignment.TopEnd) {
                AppIconImage(
                    drawable = icon,
                    contentDescription = label,
                    modifier = Modifier.size(72.dp),
                )
                if (isFavorite) {
                    FavoriteBadge(modifier = Modifier.padding(2.dp))
                }
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text      = label,
                style     = MaterialTheme.typography.labelLarge,
                color     = if (focused) FusionBlueLight else MaterialTheme.colorScheme.onSurface,
                maxLines  = 2,
                overflow  = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )
        }
    }
}
