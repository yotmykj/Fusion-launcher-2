package com.fusion.launcher.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fusion.launcher.ui.components.AppCard
import com.fusion.launcher.ui.components.FusionLogo
import com.fusion.launcher.ui.components.SearchOverlay
import com.fusion.launcher.ui.components.SectionHeader
import com.fusion.launcher.ui.components.TopBar
import com.fusion.launcher.ui.theme.FusionBlue
import com.fusion.launcher.viewmodel.LauncherUiState
import com.fusion.launcher.viewmodel.LauncherViewModel

/**
 * Root composable for the Fusion Launcher home screen.
 *
 * Layout (landscape, full-screen):
 * ┌─────────────────────────────────────────────────────────┐
 * │  TopBar           [clock]        [Search] [Settings]    │
 * ├──────────────┬──────────────────────────────────────────┤
 * │  FUSION      │  ★ FAVORITES                            │
 * │  Your apps.  │  [app] [app] [app] …                    │
 * │  Your way.   │  ALL APPS                               │
 * │              │  [app] [app] [app] …                    │
 * └──────────────┴──────────────────────────────────────────┘
 *
 * The app grid uses a [LazyVerticalGrid] with adaptive column sizing so it
 * looks good on both 1080p and 4K screens.  Section headers are inserted as
 * full-width [GridItemSpan] items.
 *
 * Focus management is handled by Jetpack Compose for TV — the D-pad moves
 * focus between cards automatically using the default focus traversal order.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: LauncherViewModel = viewModel(),
) {
    val uiState    by viewModel.uiState.collectAsState()
    val showSearch by viewModel.showSearch.collectAsState()
    val context    = LocalContext.current

    Box(modifier = modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {

            // ── Top action bar ─────────────────────────────────────────────
            TopBar(
                onSearchClick   = viewModel::toggleSearch,
                onSettingsClick = {
                    context.startActivity(
                        Intent(android.provider.Settings.ACTION_SETTINGS)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    )
                },
            )

            // ── Main content area ──────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 48.dp),
            ) {

                // ── Left panel — Fusion logo ───────────────────────────────
                FusionLogo(
                    modifier = Modifier
                        .width(280.dp)
                        .padding(top = 32.dp, end = 32.dp),
                )

                // ── Right panel — app grid ─────────────────────────────────
                when (val state = uiState) {
                    is LauncherUiState.Loading -> LoadingPanel()
                    is LauncherUiState.Error   -> ErrorPanel(state.message)
                    is LauncherUiState.Ready   -> AppGrid(
                        state     = state,
                        onLaunch  = viewModel::launchApp,
                        onFavorite = viewModel::toggleFavorite,
                    )
                }
            }
        }

        // ── Search overlay (rendered on top of everything) ─────────────────
        SearchOverlay(
            visible   = showSearch,
            onDismiss = viewModel::toggleSearch,
        )
    }
}

// ── App Grid ──────────────────────────────────────────────────────────────────

/**
 * Scrollable grid of app cards with Favorites and All Apps sections.
 *
 * Column count is adaptive: on a 4K/1080p TV screen at typical distances
 * a fixed count of 7 gives icon sizes that are comfortable to read.
 * Adjust [APP_GRID_COLUMNS] to taste.
 */
private const val APP_GRID_COLUMNS = 7

@Composable
private fun AppGrid(
    state:      LauncherUiState.Ready,
    onLaunch:   (String) -> Unit,
    onFavorite: (String) -> Unit,
) {
    LazyVerticalGrid(
        columns             = GridCells.Fixed(APP_GRID_COLUMNS),
        contentPadding      = PaddingValues(bottom = 40.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier            = Modifier.fillMaxSize(),
    ) {

        // ── Favorites section ──────────────────────────────────────────────
        if (state.favorites.isNotEmpty()) {
            item(span = { GridItemSpan(APP_GRID_COLUMNS) }) {
                SectionHeader(
                    title    = "Favorites",
                    modifier = Modifier.padding(top = 24.dp),
                )
            }

            items(
                items = state.favorites,
                key   = { "fav_${it.packageName}" },
            ) { app ->
                AppCard(
                    label       = app.label,
                    icon        = app.icon,
                    isFavorite  = true,
                    onClick     = { onLaunch(app.packageName) },
                    onLongClick = { onFavorite(app.packageName) },
                )
            }
        }

        // ── All Apps section ───────────────────────────────────────────────
        item(span = { GridItemSpan(APP_GRID_COLUMNS) }) {
            SectionHeader(
                title    = "All Apps",
                modifier = Modifier.padding(top = if (state.favorites.isEmpty()) 24.dp else 16.dp),
            )
        }

        items(
            items = state.allApps,
            key   = { "app_${it.packageName}" },
        ) { app ->
            AppCard(
                label       = app.label,
                icon        = app.icon,
                isFavorite  = app.isFavorite,
                onClick     = { onLaunch(app.packageName) },
                onLongClick = { onFavorite(app.packageName) },
            )
        }
    }
}

// ── Loading state ─────────────────────────────────────────────────────────────

@Composable
private fun LoadingPanel() {
    Box(
        modifier         = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = FusionBlue)
            Spacer(Modifier.height(16.dp))
            Text(
                text  = "Loading apps…",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

// ── Error state ───────────────────────────────────────────────────────────────

@Composable
private fun ErrorPanel(message: String) {
    Box(
        modifier         = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text  = "⚠ Failed to load apps",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.error,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text  = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
