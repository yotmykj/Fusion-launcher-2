package com.fusion.launcher.data

import android.graphics.drawable.Drawable

/**
 * Lightweight data class representing an installed, launchable application.
 *
 * @param packageName  Unique identifier used to launch the app and persist favorites.
 * @param label        Human-readable app name shown in the grid.
 * @param icon         App icon retrieved from the system; kept as [Drawable] so we
 *                     avoid an extra bitmap decode step.
 * @param isFavorite   Whether the user has marked this app as a favorite.
 *                     Persisted via [FavoritesRepository].
 */
data class AppInfo(
    val packageName: String,
    val label:       String,
    val icon:        Drawable,
    val isFavorite:  Boolean = false,
)
