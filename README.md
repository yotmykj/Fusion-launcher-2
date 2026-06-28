# Fusion Launcher 🚀

A clean, fast, open-source Android TV launcher built with **Jetpack Compose** and **Material 3**.

![Build](https://github.com/your-username/FusionLauncher/actions/workflows/android.yml/badge.svg)

---

## ✨ Features

| Feature | Details |
|---|---|
| **Home screen** | Large "FUSION" wordmark with animated tagline |
| **App grid** | All installed launchable apps in a 7-column grid |
| **Favorites** | Long-press any app to pin it; stars appear at the top |
| **D-pad navigation** | Spring-scale focus animations, full remote support |
| **Search** | Overlay placeholder (voice search coming soon) |
| **Settings** | One-press shortcut to Android system Settings |
| **Dark theme** | Near-black background with blue (#1D6FFF) accents |
| **4K ready** | Large fonts, generous spacing, adaptive layout |
| **Fast startup** | Single Activity, no heavy framework at launch |
| **No ads / no analytics** | Zero tracking, zero permissions beyond app list |

---

## 🗂 Project Structure

```
FusionLauncher/
├── app/
│   └── src/main/
│       ├── AndroidManifest.xml          # HOME launcher intent-filter
│       ├── java/com/fusion/launcher/
│       │   ├── MainActivity.kt          # Single Activity entry point
│       │   ├── data/
│       │   │   ├── AppInfo.kt           # App data model
│       │   │   ├── AppRepository.kt     # PackageManager queries
│       │   │   └── FavoritesRepository.kt  # SharedPreferences persistence
│       │   ├── viewmodel/
│       │   │   └── LauncherViewModel.kt # UI state + business logic
│       │   └── ui/
│       │       ├── theme/
│       │       │   └── FusionTheme.kt   # Colors, typography, Material 3
│       │       ├── components/
│       │       │   ├── AppCard.kt       # Focusable app tile
│       │       │   ├── AppIconImage.kt  # Drawable → Compose Image
│       │       │   ├── FavoriteBadge.kt # Star overlay on favorited cards
│       │       │   ├── FusionLogo.kt    # Animated wordmark
│       │       │   ├── SearchOverlay.kt # Full-screen search placeholder
│       │       │   ├── SectionHeader.kt # "FAVORITES" / "ALL APPS" dividers
│       │       │   └── TopBar.kt        # Clock + Search + Settings buttons
│       │       └── screens/
│       │           └── HomeScreen.kt    # Root composable
│       └── res/
│           ├── drawable/ic_banner.png   # TV app row banner (320×180)
│           ├── mipmap-*/ic_launcher*    # Launcher icons (all densities)
│           └── values/
│               ├── strings.xml
│               └── themes.xml
├── gradle/
│   ├── libs.versions.toml               # Version catalog
│   └── wrapper/gradle-wrapper.properties
├── .github/workflows/android.yml        # CI: builds debug APK on every push
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

---

## 🛠 Build Instructions

### Prerequisites

| Tool | Version |
|---|---|
| Android Studio | Ladybug (2024.2.x) or newer |
| JDK | 17 (bundled with Android Studio) |
| Android SDK | API 35 (install via SDK Manager) |
| Gradle | 8.9 (downloaded automatically by the wrapper) |

### Clone & Open

```bash
git clone https://github.com/your-username/FusionLauncher.git
cd FusionLauncher
```

Open the root folder in **Android Studio** — it will sync Gradle automatically.

### Build from Android Studio

1. **Run → Select Device** → choose your Android TV emulator or physical device.
2. Press **▶ Run** (Shift+F10).

### Build from the command line

```bash
# Debug APK
./gradlew assembleDebug

# Release APK (requires a signing keystore — see below)
./gradlew assembleRelease
```

Output: `app/build/outputs/apk/debug/app-debug.apk`

### Install on a device/emulator

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### Set as default launcher

1. Press **Home** on your TV remote.
2. Android will show the launcher chooser — select **Fusion Launcher** → **Always**.

---

## 🔑 Release Signing (optional)

```bash
keytool -genkey -v -keystore fusion.jks \
        -alias fusion -keyalg RSA -keysize 2048 -validity 10000
```

Add to `app/build.gradle.kts`:

```kotlin
signingConfigs {
    create("release") {
        storeFile     = file("fusion.jks")
        storePassword = System.getenv("STORE_PASSWORD")
        keyAlias      = "fusion"
        keyPassword   = System.getenv("KEY_PASSWORD")
    }
}
```

---

## 🤖 CI / GitHub Actions

Every push to `main`/`master` automatically:

1. Checks out the code.
2. Sets up JDK 17 with Gradle cache.
3. Runs `./gradlew assembleDebug`.
4. Uploads `app-debug.apk` as an Actions artifact (retained 7 days).

Workflow file: [`.github/workflows/android.yml`](.github/workflows/android.yml)

---

## 📐 Customisation Tips

| What | Where |
|---|---|
| Accent colour | `FusionTheme.kt` → `FusionBlue` |
| Grid column count | `HomeScreen.kt` → `APP_GRID_COLUMNS` |
| Card scale on focus | `AppCard.kt` → `targetValue = 1.10f` |
| Logo tagline | `FusionLogo.kt` |
| Minimum SDK | `app/build.gradle.kts` → `minSdk` |

---

## 📄 License

```
MIT License — Copyright (c) 2024 Fusion Launcher Contributors

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction …
```

Full license: [LICENSE](LICENSE)
