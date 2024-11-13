plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("kotlinx-serialization")
    kotlin("kapt")
    id("net.onemedics.care.convention.appdefault")
    id("net.onemedics.care.convention.compose")
    id("net.onemedics.care.convention.hilt")
    id("net.onemedics.care.convention.buildConfig")
}

android {
    namespace = "net.onemedics.exercise_player"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = libs.versions.testInstrumentationRunner.get()
        consumerProguardFiles("consumer-rules.pro")
    }
    kotlinOptions {
        jvmTarget = libs.versions.javaVer.get()
        freeCompilerArgs += "-Xjvm-default=enable"
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    }

    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = libs.versions.composeCompilerVersion.get()
}

dependencies {
    implementation(libs.bundles.materialStyleLibraries)
    implementation(libs.bundles.coroutineLibraries)
    implementation(libs.bundles.exoplayerLibraries)
    implementation(libs.bundles.coilLibraries)
    implementation(libs.jetbrains.kotlinx.serialization.json)
    implementation(libs.gson)
}