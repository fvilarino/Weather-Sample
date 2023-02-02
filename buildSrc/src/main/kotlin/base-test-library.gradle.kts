import org.gradle.api.JavaVersion

plugins {
    id("com.android.test")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = Versions.compileSdkVersion

    defaultConfig {
        minSdk = Versions.minSdkVersion
        targetSdk = Versions.targetSdkVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = Config.Compiler.javaVersion
        targetCompatibility = Config.Compiler.javaVersion
    }

    kotlinOptions {
        jvmTarget = Config.Compiler.jvmTarget
    }
}
