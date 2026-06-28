package com.fusion.launcher.data

import android.content.Context
import android.content.SharedPreferences

/**
 * Persists the user's favorite app package names using [SharedPreferences].
 *
 * This intentionally avoids a database or dependency injection framework to
 * keep the launcher fast to start.  Favorites are stored as a [Set<String>]
 * under a single key.
 */
class FavoritesRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /** Returns the current set of favorited package names. */
    fun getFavorites(): Set<String> =
        prefs.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()

    /** Adds [packageName] to favorites and persists immediately. */
    fun addFavorite(packageName: String) {
        val updated = getFavorites().toMutableSet().apply { add(packageName) }
        prefs.edit().putStringSet(KEY_FAVORITES, updated).apply()
    }

    /** Removes [packageName] from favorites and persists immediately. */
    fun removeFavorite(packageName: String) {
        val updated = getFavorites().toMutableSet().apply { remove(packageName) }
        prefs.edit().putStringSet(KEY_FAVORITES, updated).apply()
    }

    /** Toggles the favorite state of [packageName] and returns the new state. */
    fun toggleFavorite(packageName: String): Boolean {
        val favorites = getFavorites()
        return if (packageName in favorites) {
            removeFavorite(packageName)
            false
        } else {
            addFavorite(packageName)
            true
        }
    }

    companion object {
        private const val PREFS_NAME   = "fusion_launcher_prefs"
        private const val KEY_FAVORITES = "favorites"
    }
}
