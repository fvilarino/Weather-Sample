plugins {
    id(Depends.ModulePlugins.libraryPlugin)
    id(Depends.ModulePlugins.kotlinPlugin)
    id(Depends.ModulePlugins.daggerHiltPlugin)
    kotlin(Depends.ModulePlugins.kotlinKapt)
}

android {
    compileSdkVersion(Versions.BuildConfig.compileSdkVersion)

    defaultConfig {
        minSdkVersion(Versions.BuildConfig.minSdkVersion)
        targetSdkVersion(Versions.BuildConfig.targetSdkVersion)
        versionCode = Versions.BuildConfig.appVersionCode
        versionName = Versions.BuildConfig.appVersionName
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = Config.Compiler.jvmTarget
        Config.Compiler.freeCompilerArgs.forEach { arg ->
            freeCompilerArgs = freeCompilerArgs + arg
        }
    }
}

dependencies {

    implementation(project(":core:repository"))
    implementation(project(":utils"))

    implementation(Depends.Android.ktx)
    implementation(Depends.Kotlin.coroutinesCore)

    implementation(Depends.Hilt.daggerHiltAndroid)
    kapt(Depends.Hilt.daggerHiltAndroidCompiler)

    implementation(Depends.Logging.timber)

    testImplementation(Depends.TestLibraries.jUnit)
    androidTestImplementation(Depends.TestLibraries.androidJUnit)
    androidTestImplementation(Depends.TestLibraries.espressoCore)
}
