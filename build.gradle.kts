// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.13.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}
dependencies{
    val navVersion = "2.7.3"
    "androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion"
}