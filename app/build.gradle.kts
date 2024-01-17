plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
    kotlin("kapt")
}

android {
    namespace = "uz.coder.davomatapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "uz.coder.davomatapp"
        minSdk = 23
        targetSdk = 34
        versionCode = 2
        versionName = "2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures{
    viewBinding =  true
    }
}

dependencies {
    implementation ("com.google.dagger:dagger:2.48.1")
    kapt ("com.google.dagger:dagger-compiler:2.48.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("com.android.support:support-annotations:28.0.0")
    implementation("androidx.annotation:annotation:1.7.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(kotlin("reflect"))
}