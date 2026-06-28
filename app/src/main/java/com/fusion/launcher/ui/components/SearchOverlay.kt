package com.fusion.launcher.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fusion.launcher.ui.theme.FusionBackground
import com.fusion.launcher.ui.theme.FusionBlue
import com.fusion.launcher.ui.theme.FusionSurface

/**
 * Full-screen search overlay — currently a placeholder.
 *
 * A production implementation would integrate with the system voice-search
 * intent or an on-screen keyboard.  The slide + fade animation makes the
 * transition feel polished even before real search is wired in.
 *
 * @param visible   Controls whether the overlay is on screen.
 * @param onDismiss Called when the user presses Back or the close button.
 */
@Composable
fun SearchOverlay(
    visible:   Boolean,
    onDismiss: () -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter   = fadeIn() + slideInVertically(initialOffsetY = { -it / 4 }),
        exit    = fadeOut() + slideOutVertically(targetOffsetY = { -it / 4 }),
    ) {
        // Semi-transparent scrim behind the dialog
        Box(
            modifier         = Modifier
                .fillMaxSize()
                .background(Color(0xCC000000)),
            contentAlignment = Alignment.Center,
        ) {
            Surface(
                modifier        = Modifier
                    .widthIn(max = 640.dp)
                    .padding(24.dp),
                shape           = RoundedCornerShape(16.dp),
                color           = FusionSurface,
                shadowElevation = 24.dp,
            ) {
                Column(
                    modifier            = Modifier.padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    Icon(
                        imageVector        = Icons.Filled.Search,
                        contentDescription = null,
                        tint               = FusionBlue,
                        modifier           = Modifier.size(48.dp),
                    )

                    Text(
                        text  = "Search",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Text(
                        text  = "Voice search and on-screen keyboard\ncoming in a future update.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    Spacer(Modifier.height(8.dp))

                    // ── Close button ────────────────────────────────────────
                    var closeFocused by remember { mutableStateOf(false) }

                    Surface(
                        onClick  = onDismiss,
                        modifier = Modifier
                            .onFocusChanged { closeFocused = it.isFocused }
                            .focusable(),
                        shape    = RoundedCornerShape(8.dp),
                        color    = if (closeFocused) FusionBlue else FusionBackground,
                    ) {
                        Row(
                            modifier              = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                            verticalAlignment     = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Icon(
                                imageVector        = Icons.Filled.Close,
                                contentDescription = null,
                                tint               = if (closeFocused) Color.White
                                                     else MaterialTheme.colorScheme.onBackground,
                                modifier           = Modifier.size(18.dp),
                            )
                            Text(
                                text  = "Close",
                                color = if (closeFocused) Color.White
                                        else MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.labelLarge,
                            )
                        }
                    }
                }
            }
        }
    }
}
