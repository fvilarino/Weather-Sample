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

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = Config.Compiler.jvmTarget
        freeCompilerArgs = freeCompilerArgs + Config.Compiler.freeCompilerArgs
    }
    kapt {
        javacOptions {
            option("-Xmaxerrs", 1000)
        }
    }
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.espresso.core)
}
