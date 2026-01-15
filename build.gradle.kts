// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    dependencies{
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.9.6")
    }
}
plugins {
    id("com.android.application") version "8.13.2" apply false
    //noinspection NewerVersionAvailable
    id("org.jetbrains.kotlin.android") version "2.1.0" apply false
    id("com.android.library") version "8.13.2" apply false
    id("com.google.devtools.ksp") version "2.1.10-1.0.30" apply false
    id("org.jetbrains.kotlin.plugin.compose").version("2.0.20").apply(false)
    id("com.google.dagger.hilt.android") version "2.57.2" apply false
}