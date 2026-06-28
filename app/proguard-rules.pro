# Fusion Launcher — ProGuard rules
# Keep all Compose runtime internals intact
-keep class androidx.compose.** { *; }
-keepclassmembers class androidx.compose.** { *; }

# Keep all ViewModel subclasses (accessed by name via reflection in Compose)
-keep class * extends androidx.lifecycle.ViewModel { *; }

# Keep AppInfo data class fields (may be accessed via reflection)
-keep class com.fusion.launcher.data.AppInfo { *; }

# Preserve Kotlin metadata so kotlinx.serialization / reflection works
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod

# Remove logging in release builds
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
}
