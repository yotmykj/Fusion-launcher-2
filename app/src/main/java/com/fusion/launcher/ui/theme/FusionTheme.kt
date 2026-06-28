package com.fusion.launcher.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography

// ── Palette ──────────────────────────────────────────────────────────────────
// Designed for a dark home theatre environment.  The accent blue (#1D6FFF) is
// vivid enough to read on a 4K screen viewed from 3 metres.

val FusionBlue        = Color(0xFF1D6FFF)   // primary accent
val FusionBlueLight   = Color(0xFF5A9BFF)   // focused / hovered state
val FusionBlueDark    = Color(0xFF0048CC)   // pressed state
val FusionBackground  = Color(0xFF0A0E1A)   // near-black with a blue tint
val FusionSurface     = Color(0xFF141929)   // card / tile background
val FusionSurface2    = Color(0xFF1C2237)   // elevated surface (dialogs, rows)
val FusionOnSurface   = Color(0xFFE8EEFF)   // primary text on dark surfaces
val FusionOnSurface2  = Color(0xFFA0B9FF)   // secondary / muted text
val FusionDivider     = Color(0x33A0B9FF)   // subtle separator lines

// ── Color Scheme ─────────────────────────────────────────────────────────────
private val FusionColorScheme = darkColorScheme(
    primary          = FusionBlue,
    onPrimary        = Color.White,
    primaryContainer = FusionBlueDark,
    secondary        = FusionBlueLight,
    onSecondary      = Color.White,
    background       = FusionBackground,
    onBackground     = FusionOnSurface,
    surface          = FusionSurface,
    onSurface        = FusionOnSurface,
    surfaceVariant   = FusionSurface2,
    onSurfaceVariant = FusionOnSurface2,
    outline          = FusionDivider,
)

// ── Typography ────────────────────────────────────────────────────────────────
// Using the default sans-serif so the font is always present on TV devices.
// Sizes are intentionally large — TV UIs are read at ~3 m distance.

private val FusionTypography = Typography(
    displayLarge  = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold,   fontSize = 72.sp,  letterSpacing = (-1).sp),
    displayMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold,   fontSize = 48.sp),
    headlineLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold,   fontSize = 36.sp),
    headlineMedium= TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Medium, fontSize = 28.sp),
    titleLarge    = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Medium, fontSize = 22.sp),
    titleMedium   = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, fontSize = 18.sp),
    bodyLarge     = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, fontSize = 16.sp),
    bodyMedium    = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, fontSize = 14.sp),
    labelLarge    = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Medium, fontSize = 14.sp),
    labelMedium   = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, fontSize = 12.sp),
)

// ── Theme composable ──────────────────────────────────────────────────────────

/**
 * Applies the Fusion Launcher visual theme to all child composables.
 * Always dark — a TV launcher has no need for a light variant.
 */
@Composable
fun FusionTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = FusionColorScheme,
        typography  = FusionTypography,
        content     = content,
    )
}
