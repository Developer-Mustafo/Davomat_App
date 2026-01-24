@file:Suppress("DEPRECATION")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.safe.args)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.dagger.hilt)
}

android {
    namespace = "uz.coder.davomatapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "uz.coder.davomatapp"
        minSdk = 26
        targetSdk = 36
        versionCode = 3
        versionName = "2.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ndk{
            abiFilters.addAll(listOf("armeabi-v7a","arm64-v8a","x86","x86_64"))
        }
        ndkVersion="29.0.14206865"
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"  // Optional: specify CMake version
        }
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
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    //compose
    buildFeatures {
        compose = true
        viewBinding = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //compose
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.compiler)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    //compose

    //compose+xml
    implementation(libs.activity.compose)
    //compose+xml

    //composeViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    //composeViewModel

    implementation(libs.androidx.compose.material.icons.extended)

    //filePicker
    implementation(libs.filepicker)
    //filePicker

    //get-string-as-response
    implementation(libs.converter.scalars)
    //get-string-as-response

    //compose-navigation
    implementation(libs.androidx.navigation.compose)
    //compose-navigation

    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    //hilt

    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.lottie)
    implementation (libs.gson)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.compose.material3)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.room.ktx)
    implementation(libs.support.annotations)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(kotlin("reflect"))
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))
}