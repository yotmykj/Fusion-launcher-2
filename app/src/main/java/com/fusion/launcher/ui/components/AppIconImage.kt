package com.fusion.launcher.ui.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.core.graphics.drawable.toBitmap

/**
 * Renders an Android [Drawable] (app icon) inside a Compose [Image].
 *
 * [toBitmap] (from androidx.core:core-ktx) handles all Drawable sub-types
 * including AdaptiveIconDrawable (API 26+), so launcher icons always render
 * correctly regardless of whether the app uses round / adaptive icons.
 *
 * @param drawable           The [Drawable] to display.
 * @param contentDescription Accessibility label for screen readers / TalkBack.
 * @param modifier           Modifier forwarded to [Image].
 */
@Composable
fun AppIconImage(
    drawable:           Drawable,
    contentDescription: String,
    modifier:           Modifier = Modifier,
) {
    // Cache the bitmap conversion: `remember(drawable)` re-converts only when
    // the drawable instance changes (e.g. after a package update).
    val bitmap = remember(drawable) {
        drawable.toBitmap(width = 144, height = 144).asImageBitmap()
    }

    Image(
        bitmap             = bitmap,
        contentDescription = contentDescription,
        contentScale       = ContentScale.Fit,
        modifier           = modifier,
    )
}
