plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    compileSdk = Versions.compileSdkVersion
    namespace = "com.francescsoftware.weathersample"

    defaultConfig {
        applicationId = Config.Application.applicationId
        minSdk = Versions.minSdkVersion
        targetSdk = Versions.targetSdkVersion
        versionCode = Versions.appVersionCode
        versionName = Versions.appVersionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("./certs/debug.keystore")
        }

        create("release") {
            val releasePropertiesFile = rootProject.file("./certs/release.properties")
            if (releasePropertiesFile.exists()) {
                parseConfig(releasePropertiesFile)
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
    }

    compileOptions {
        sourceCompatibility = Config.Compiler.javaVersion
        targetCompatibility = Config.Compiler.javaVersion
    }

    kotlinOptions {
        jvmTarget = Config.Compiler.jvmTarget
        freeCompilerArgs = freeCompilerArgs + Config.Compiler.kotlinTimeFreeCompilerArgs
    }

    kapt {
        javacOptions {
            option("-Xmaxerrs", 1000)
        }
    }
}

fun com.android.build.api.dsl.SigningConfig.parseConfig(file: File) {
    file.useLines { lines ->
        lines.forEach { line ->
            when {
                line.startsWith("store:") -> storeFile = rootProject.file(line.valueAfterColon())
                line.startsWith("alias:") -> keyAlias = line.valueAfterColon()
                line.startsWith("storePass:") -> storePassword = line.valueAfterColon()
                line.startsWith("keyPass:") -> keyPassword = line.valueAfterColon()
            }
        }
    }
}

fun String.valueAfterColon() = substring(indexOf(":") + 1).trim()

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

    implementation("com.airbnb.android:showkase:1.0.0-beta17")
    kapt("com.airbnb.android:showkase-processor:1.0.0-beta17")

    // store
    implementation(libs.androidx.datastore.datastore)
    implementation(libs.com.google.protobuf.protobuf.javalite)

    // dagger
    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)

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
