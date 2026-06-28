package com.fusion.launcher.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fusion.launcher.ui.theme.FusionBlue
import com.fusion.launcher.ui.theme.FusionOnSurface2

/**
 * The "FUSION" wordmark displayed prominently on the home screen.
 *
 * The tagline beneath it has a gentle pulse animation — subtle enough to be
 * elegant on a TV but noticeable enough to make the launcher feel alive.
 *
 * @param modifier Forwarded to the outer [Column].
 */
@Composable
fun FusionLogo(modifier: Modifier = Modifier) {
    // Subtle tagline pulse — 3-second cycle, 80%→100% alpha
    val infiniteTransition = rememberInfiniteTransition(label = "logoPulse")
    val taglineAlpha by infiniteTransition.animateFloat(
        initialValue   = 0.75f,
        targetValue    = 1.00f,
        animationSpec  = infiniteRepeatable(
            animation  = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "taglineAlpha",
    )

    Column(
        modifier            = modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        // Main wordmark — large, bold, blue
        Text(
            text       = "FUSION",
            fontSize   = 68.sp,
            fontWeight = FontWeight.ExtraBold,
            color      = FusionBlue,
            letterSpacing = (-1).sp,
        )

        Spacer(Modifier.height(4.dp))

        // Tagline below the wordmark
        Text(
            text     = "Your apps. Your way.",
            style    = MaterialTheme.typography.titleMedium,
            color    = FusionOnSurface2,
            modifier = Modifier.alpha(taglineAlpha),
        )
    }
}
