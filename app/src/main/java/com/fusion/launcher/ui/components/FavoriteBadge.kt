package com.fusion.launcher.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fusion.launcher.ui.theme.FusionBlue

/**
 * A small gold star badge rendered in the top-right corner of a focused
 * or favorited [AppCard].  Kept as a separate composable so it can be
 * toggled independently without recomposing the entire card.
 */
@Composable
fun FavoriteBadge(modifier: Modifier = Modifier) {
    Box(
        modifier        = modifier
            .size(20.dp)
            .background(color = Color(0xFFFFD700), shape = CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector        = Icons.Filled.Star,
            contentDescription = "Favorite",
            tint               = Color.White,
            modifier           = Modifier.size(12.dp),
        )
    }
}
