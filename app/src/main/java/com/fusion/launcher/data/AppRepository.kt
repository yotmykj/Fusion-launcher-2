package com.fusion.launcher.data

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Queries the system [PackageManager] for all installed apps that have a
 * launchable main activity.  Work is performed on [Dispatchers.IO] so the
 * UI thread is never blocked during the (sometimes slow) package scan.
 */
class AppRepository(
    private val context:             Context,
    private val favoritesRepository: FavoritesRepository,
) {

    /**
     * Returns a sorted list of [AppInfo] objects for every launchable app,
     * with [AppInfo.isFavorite] populated from [FavoritesRepository].
     *
     * Fusion Launcher itself is excluded from the list.
     */
    suspend fun getInstalledApps(): List<AppInfo> = withContext(Dispatchers.IO) {
        val pm        = context.packageManager
        val favorites = favoritesRepository.getFavorites()

        // Query for activities that respond to the main/launcher intent
        val mainIntent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        pm.queryIntentActivities(mainIntent, PackageManager.GET_RESOLVED_FILTER)
            .asSequence()
            .filter { it.activityInfo.packageName != context.packageName } // exclude self
            .map { resolveInfo ->
                AppInfo(
                    packageName = resolveInfo.activityInfo.packageName,
                    label       = resolveInfo.loadLabel(pm).toString(),
                    icon        = resolveInfo.loadIcon(pm),
                    isFavorite  = resolveInfo.activityInfo.packageName in favorites,
                )
            }
            .sortedWith(
                compareByDescending<AppInfo> { it.isFavorite }
                    .thenBy { it.label.lowercase() }
            )
            .toList()
    }
}
