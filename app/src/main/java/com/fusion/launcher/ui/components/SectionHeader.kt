package com.fusion.launcher.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fusion.launcher.ui.theme.FusionBlue
import com.fusion.launcher.ui.theme.FusionDivider

/**
 * A horizontal section header with a coloured label and a divider line.
 * Used to separate "Favorites" from "All Apps" in the home screen grid.
 *
 * @param title    Label text displayed on the left.
 * @param modifier Forwarded to the root [Row].
 */
@Composable
fun SectionHeader(title: String, modifier: Modifier = Modifier) {
    Row(
        modifier          = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text  = title.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = FusionBlue,
        )
        Spacer(Modifier.width(12.dp))
        Divider(
            modifier  = Modifier
                .weight(1f)
                .height(1.dp),
            color     = FusionDivider,
            thickness = 1.dp,
        )
    }
}
