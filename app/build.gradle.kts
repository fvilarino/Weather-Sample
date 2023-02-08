import java.io.FileInputStream
import java.util.Properties

plugins {
    id("weathersample.android.application")
    id("weathersample.android.hilt")
}

android {
    namespace = "com.francescsoftware.weathersample"

    defaultConfig {
        applicationId = "com.francescsoftware.weathersample"
    }

    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("./certs/debug.keystore")
        }

        create("release") {
            val releasePropertiesFile = rootProject.file("./certs/release.properties")
            if (releasePropertiesFile.exists()) {
                val properties = loadSigningProperties(releasePropertiesFile)
                storeFile = rootProject.file(properties["store"] as String)
                keyAlias = properties["alias"] as String
                storePassword = properties["storePass"] as String
                keyPassword = properties["keyPass"] as String
            } else {
                storeFile = rootProject.file(System.getenv("store"))
                keyAlias = System.getenv("alias")
                storePassword = System.getenv("storePass")
                keyPassword = System.getenv("keyPass")
            }
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".dev"
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "retrofit2.pro",
                "kotlin-serialization.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.time.ExperimentalTime"
    }
}

fun loadSigningProperties(file: File) =
    Properties().apply {
        load(FileInputStream(file))
    }

dependencies {
    implementation(project(":business:interactor:city:api"))
    implementation(project(":business:interactor:city:impl"))
    implementation(project(":business:interactor:weather:api"))
    implementation(project(":business:interactor:weather:impl"))

    implementation(project(":core:dispatcher"))
    implementation(project(":core:network"))
    implementation(project(":core:time:api"))
    implementation(project(":core:time:impl"))

    implementation(project(":data:storage:city:api"))
    implementation(project(":data:storage:city:impl"))
    implementation(project(":data:repository:city:api"))
    implementation(project(":data:repository:city:impl"))
    implementation(project(":data:repository:recents:api"))
    implementation(project(":data:repository:recents:impl"))
    implementation(project(":data:repository:weather:api"))
    implementation(project(":data:repository:weather:impl"))

    implementation(project(":presentation:feature:city"))
    implementation(project(":presentation:feature:landing"))
    implementation(project(":presentation:feature:weather"))
    implementation(project(":presentation:shared:assets"))
    implementation(project(":presentation:shared:composable"))
    implementation(project(":presentation:shared:lookup:api"))
    implementation(project(":presentation:shared:lookup:impl"))
    implementation(project(":presentation:shared:mvi"))
    implementation(project(":presentation:shared:route"))
    implementation(project(":presentation:shared:styles"))

    implementation(project(":utils"))

    // android
    implementation(libs.androidx.core.core.ktx)
    implementation(libs.androidx.app.compat)
    implementation(libs.androidx.navigation.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.navigation.ui.ktx)
    implementation(libs.bundles.lifecycle)
    implementation(libs.com.google.android.material)

    // store
    implementation(libs.androidx.datastore.datastore)
    implementation(libs.com.google.protobuf.protobuf.javalite)

    // kotlin
    implementation(libs.bundles.coroutines)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)

    // network
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)

    // logging
    implementation(libs.com.jakewharton.timber)

    // test
    testImplementation(libs.bundles.junit)
    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.test.espresso.espresso.core)
}
