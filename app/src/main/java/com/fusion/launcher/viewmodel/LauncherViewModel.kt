package com.fusion.launcher.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fusion.launcher.data.AppInfo
import com.fusion.launcher.data.AppRepository
import com.fusion.launcher.data.FavoritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * [LauncherViewModel] is the single source of truth for the launcher UI.
 *
 * It owns:
 *  - [LauncherUiState] — the complete UI state exposed to composables.
 *  - The app list loading logic (via [AppRepository]).
 *  - Intent dispatch for launching apps.
 *  - Favorite toggle logic (via [FavoritesRepository]).
 *
 * Using [AndroidViewModel] gives us a [Application] reference without leaking
 * an Activity context — important since this is a long-lived launcher process.
 */
class LauncherViewModel(application: Application) : AndroidViewModel(application) {

    private val favoritesRepository = FavoritesRepository(application)
    private val appRepository       = AppRepository(application, favoritesRepository)

    // ── UI State ────────────────────────────────────────────────────────────
    private val _uiState = MutableStateFlow<LauncherUiState>(LauncherUiState.Loading)
    val uiState: StateFlow<LauncherUiState> = _uiState.asStateFlow()

    // ── Current search query ────────────────────────────────────────────────
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // ── Show search bar ─────────────────────────────────────────────────────
    private val _showSearch = MutableStateFlow(false)
    val showSearch: StateFlow<Boolean> = _showSearch.asStateFlow()

    init {
        loadApps()
    }

    // ── Public actions ───────────────────────────────────────────────────────

    /** (Re-)loads all installed apps from the package manager. */
    fun loadApps() {
        viewModelScope.launch {
            _uiState.value = LauncherUiState.Loading
            try {
                val apps = appRepository.getInstalledApps()
                _uiState.value = LauncherUiState.Ready(
                    allApps   = apps,
                    favorites = apps.filter { it.isFavorite },
                )
            } catch (e: Exception) {
                _uiState.value = LauncherUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    /**
     * Launches the app identified by [packageName].
     * Uses [Intent.FLAG_ACTIVITY_NEW_TASK] as required from a non-Activity context.
     */
    fun launchApp(packageName: String) {
        val intent = getApplication<Application>()
            .packageManager
            .getLaunchIntentForPackage(packageName)
            ?.apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
        intent?.let { getApplication<Application>().startActivity(it) }
    }

    /**
     * Toggles the favorite state of the given app and reloads the list so
     * the UI reflects the change immediately.
     */
    fun toggleFavorite(packageName: String) {
        favoritesRepository.toggleFavorite(packageName)
        loadApps()
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleSearch() {
        _showSearch.value = !_showSearch.value
        if (!_showSearch.value) _searchQuery.value = ""
    }
}

// ── UI State sealed class ────────────────────────────────────────────────────

/** Represents the three possible states of the launcher home screen. */
sealed class LauncherUiState {
    /** App list is being loaded from the package manager. */
    data object Loading : LauncherUiState()

    /**
     * App list loaded successfully.
     * @param allApps   Every launchable app, alphabetically sorted.
     * @param favorites Only apps the user has starred.
     */
    data class Ready(
        val allApps:   List<AppInfo>,
        val favorites: List<AppInfo>,
    ) : LauncherUiState()

    /** Something went wrong during loading. */
    data class Error(val message: String) : LauncherUiState()
}
