plugins {
    id(Depends.ModulePlugins.libraryPlugin)
    id(Depends.ModulePlugins.kotlinPlugin)
    id(Depends.ModulePlugins.daggerHiltPlugin)
    id(Depends.ModulePlugins.kotlinParcelizePlugin)
    id(Depends.ModulePlugins.kotlinSerializationPlugin)
    id(Depends.ModulePlugins.protoBuf) version Versions.Plugin.protobufPluginVersion
    kotlin(Depends.ModulePlugins.kotlinKapt)
}

android {
    compileSdk = Versions.BuildConfig.compileSdkVersion

    defaultConfig {
        minSdk = Versions.BuildConfig.minSdkVersion
        targetSdk = Versions.BuildConfig.targetSdkVersion
        testInstrumentationRunner = Depends.TestLibraries.testRunner
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        dataBinding = true
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = Config.Compiler.jvmTarget
        freeCompilerArgs = freeCompilerArgs + Config.Compiler.freeCompilerArgs
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.composeVersion
    }
}

dependencies {

    implementation(project(":business:interactor"))
    implementation(project(":core:type"))
    implementation(project(":presentation:shared"))
    implementation(project(":presentation:storage"))
    implementation(project(":styles"))
    implementation(project(":utils"))

    // compose
    implementation(Depends.Compose.composeUi)
    implementation(Depends.Compose.composeMaterial)
    implementation(Depends.Compose.composeUiTooling)
    implementation(Depends.Compose.activityCompose)
    implementation(Depends.Compose.navigationCompose)

    implementation(Depends.Android.ktx)
    implementation(Depends.Android.appCompat)
    implementation(Depends.Material.material)
    implementation(Depends.Android.navigationFragment)
    implementation(Depends.Android.navigationUi)
    implementation(Depends.Android.dataStore)
    implementation(Depends.Google.protoBufJavaLite)

    implementation(Depends.Android.archLifeCycleViewModel)
    implementation(Depends.Android.lifecycleCommon)

    implementation(Depends.Kotlin.coroutinesCore)
    implementation(Depends.Kotlin.coroutinesAndroid)
    implementation(Depends.Kotlin.kotlinSerialization)

    implementation(Depends.Hilt.daggerHiltAndroid)
    kapt(Depends.Hilt.daggerHiltAndroidCompiler)
    implementation(Depends.Hilt.androidxHiltNavigation)

    implementation(Depends.Logging.timber)

    testImplementation(Depends.TestLibraries.jUnit)
    androidTestImplementation(Depends.TestLibraries.androidJUnit)
    androidTestImplementation(Depends.TestLibraries.espressoCore)
}
