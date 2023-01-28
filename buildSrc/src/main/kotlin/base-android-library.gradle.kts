import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = Versions.compileSdkVersion

    defaultConfig {
        minSdk = Versions.minSdkVersion
        targetSdk = Versions.targetSdkVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = Config.Compiler.javaVersion
        targetCompatibility = Config.Compiler.javaVersion
    }
    kotlinOptions {
        jvmTarget = Config.Compiler.jvmTarget
        freeCompilerArgs = freeCompilerArgs + Config.Compiler.kotlinTimeFreeCompilerArgs
    }
    testOptions {
        unitTests.all { test ->
            test.useJUnitPlatform {
                includeEngines("junit-jupiter", "junit-vintage")
            }
            test.testLogging {
                events("passed", "skipped", "failed")
                showStandardStreams = true
                showStackTraces = true
                showCauses = true
                exceptionFormat = TestExceptionFormat.FULL
            }
        }
    }
}
