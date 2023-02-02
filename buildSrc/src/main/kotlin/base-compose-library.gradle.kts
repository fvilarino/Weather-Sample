plugins {
    id("base-android-library")
    kotlin("kapt")
}

val catalogs = extensions.getByType<VersionCatalogsExtension>()
val composeVersion = catalogs
    .named("libs")
    .findVersion("androidx-compose-compiler-version")
    .get()
    .requiredVersion

android {
    buildFeatures {
        compose = true
    }
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + Config.Compiler.composeFreeCompileArgs
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }
}

dependencies {
    implementation("com.airbnb.android:showkase:1.0.0-beta17")
    kapt("com.airbnb.android:showkase-processor:1.0.0-beta17")
}
