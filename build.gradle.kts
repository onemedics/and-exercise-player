plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("kotlinx-serialization")
    kotlin("kapt")
    id ("net.onemedics.care.convention.appdefault")
    id ("net.onemedics.care.convention.compose")
    id ("net.onemedics.care.convention.hilt")
    id ("net.onemedics.care.convention.buildConfig")
}

android {
    namespace = "net.onemedics.exercise_player"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = libs.versions.testInstrumentationRunner.get()
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
       jvmTarget = libs.versions.javaVer.get()
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.google.android.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.test)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.bundles.exoplayerLibraries)
}