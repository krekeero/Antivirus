# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If you are using a version of Gradle lower than 2.1, change
# 'implementation' to 'compile' in the dependencies section of
# your app-level build.gradle file.

# Keep ViewBinding classes
-keep class * implements androidx.viewbinding.ViewBinding {
    public static *** bind(android.view.View);
    public static *** inflate(...);
}

# Keep ViewModel classes
-keep class * extends androidx.lifecycle.ViewModel {
    <init>();
}

# Keep LiveData classes
-keep class * extends androidx.lifecycle.LiveData {
    <init>();
}

# Keep Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile