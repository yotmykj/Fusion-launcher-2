package com.fusion.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.fusion.launcher.ui.screens.HomeScreen
import com.fusion.launcher.ui.theme.FusionBackground
import com.fusion.launcher.ui.theme.FusionTheme

/**
 * MainActivity — the single entry point for Fusion Launcher.
 *
 * Design decisions:
 *  - Extends [ComponentActivity] (not AppCompatActivity) to keep the APK slim.
 *  - [enableEdgeToEdge] ensures the Compose UI fills the entire 4K panel,
 *    with no system window insets eating into the usable area.
 *  - The Activity is declared with `launchMode="singleTask"` in the manifest
 *    so pressing Home while already in the launcher re-uses this instance
 *    rather than creating a new stack entry.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Edge-to-edge: Compose owns the entire display area including
        // status/nav bars.  On TV there are typically no system bars anyway.
        enableEdgeToEdge()

        setContent {
            FusionTheme {
                HomeScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(FusionBackground),
                )
            }
        }
    }
}
