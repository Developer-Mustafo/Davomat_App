// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    dependencies{
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.9.3")
    }
}
plugins {
    id("com.android.application") version "8.13.0" apply false
    //noinspection NewerVersionAvailable
    id("org.jetbrains.kotlin.android") version "2.2.0" apply false
    id("com.android.library") version "8.13.0" apply false
}