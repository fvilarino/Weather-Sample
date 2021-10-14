plugins {
    id(Depends.ModulePlugins.libraryPlugin)
    id(Depends.ModulePlugins.kotlinPlugin)
    id(Depends.ModulePlugins.daggerHiltPlugin)
    id(Depends.ModulePlugins.kotlinParcelizePlugin)
    id(Depends.ModulePlugins.safeArgsPlugin)
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = Config.Compiler.jvmTarget
        freeCompilerArgs = freeCompilerArgs + Config.Compiler.freeCompilerArgs
    }
}

dependencies {

    implementation(project(":business:interactor"))
    implementation(project(":core:type"))
    implementation(project(":presentation:shared"))
    implementation(project(":styles"))
    implementation(project(":utils"))

    implementation(Depends.Android.ktx)
    implementation(Depends.Android.appCompat)
    implementation(Depends.Material.material)
    implementation(Depends.Android.constraintLayout)
    implementation(Depends.Android.navigationFragment)
    implementation(Depends.Android.navigationUi)

    implementation(Depends.Android.archLifeCycleViewModel)
    implementation(Depends.Android.lifecycleCommon)

    implementation(Depends.Kotlin.coroutinesCore)
    implementation(Depends.Kotlin.coroutinesAndroid)

    implementation(Depends.Hilt.daggerHiltAndroid)
    kapt(Depends.Hilt.daggerHiltAndroidCompiler)
    kapt(Depends.Hilt.androidxHiltCompiler)

    implementation(Depends.Logging.timber)

    testImplementation(Depends.TestLibraries.jUnit)
    androidTestImplementation(Depends.TestLibraries.androidJUnit)
    androidTestImplementation(Depends.TestLibraries.espressoCore)
}
