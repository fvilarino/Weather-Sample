plugins {
    id(Depends.ModulePlugins.libraryPlugin)
    id(Depends.ModulePlugins.kotlinPlugin)
    id(Depends.ModulePlugins.daggerHiltPlugin)
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
        kotlinCompilerExtensionVersion = Versions.Compose.composeCompilerVersion
    }
}

dependencies {

    implementation(project(":business:interactor:city:api"))
    implementation(project(":core:lookup:api"))
    implementation(project(":core:type"))
    implementation(project(":presentation:feature:navigation:api"))
    implementation(project(":presentation:shared:composable"))
    implementation(project(":presentation:shared:mvi"))
    implementation(project(":presentation:shared:styles"))

    // compose
    implementation(Depends.Compose.composeUi)
    implementation(Depends.Compose.composeMaterial)
    implementation(Depends.Compose.composeUiTooling)
    implementation(Depends.Compose.navigationCompose)
    implementation(Depends.Android.ktx)
    implementation(Depends.Android.archLifeCycleViewModel)

    implementation(Depends.Hilt.daggerHiltAndroid)
    kapt(Depends.Hilt.daggerHiltAndroidCompiler)
    implementation(Depends.Hilt.androidxHiltNavigation)

    implementation(Depends.Logging.timber)

    testImplementation(Depends.TestLibraries.jUnit)
    androidTestImplementation(Depends.TestLibraries.androidJUnit)
    androidTestImplementation(Depends.TestLibraries.espressoCore)
}
