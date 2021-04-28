plugins {
    id(Depends.ModulePlugins.applicationPlugin)
    id(Depends.ModulePlugins.kotlinPlugin)
    id(Depends.ModulePlugins.daggerHiltPlugin)
    id(Depends.ModulePlugins.versionsPlugin) version Versions.Plugin.versionsPluginVersion
    kotlin(Depends.ModulePlugins.kotlinKapt)
}

android {
    compileSdkVersion(Versions.BuildConfig.compileSdkVersion)

    defaultConfig {
        applicationId = Config.Application.applicationId
        minSdkVersion(Versions.BuildConfig.minSdkVersion)
        targetSdkVersion(Versions.BuildConfig.targetSdkVersion)
        versionCode = Versions.BuildConfig.appVersionCode
        versionName = Versions.BuildConfig.appVersionName
        testInstrumentationRunner = Depends.TestLibraries.testRunner
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = Config.Compiler.jvmTarget
    }
    kapt {
        javacOptions {
            option("-Xmaxerrs", 1000)
        }
    }
}

dependencies {
    implementation(project(":business:interactor"))
    implementation(project(":data:repository"))
    implementation(project(":presentation:feature"))
    implementation(project(":presentation:shared"))
    implementation(project(":styles"))
    implementation(project(":utils"))

    // android
    implementation(Depends.Android.ktx)
    implementation(Depends.Android.appCompat)
    implementation(Depends.Android.constraintLayout)
    implementation(Depends.Android.navigationFragment)
    implementation(Depends.Android.navigationUi)
    implementation(Depends.Android.archLifeCycleViewModel)
    implementation(Depends.Android.lifecycleCommon)
    implementation(Depends.Material.material)

    // dagger
    implementation(Depends.Hilt.daggerHiltAndroid)
    kapt(Depends.Hilt.daggerHiltAndroidCompiler)
    implementation(Depends.Hilt.androidxHiltLifecycleViewModel)
    kapt(Depends.Hilt.androidxHiltCompiler)

    // kotlin
    implementation(Depends.Kotlin.coroutinesCore)
    implementation(Depends.Kotlin.coroutinesAndroid)
    implementation(Depends.Kotlin.kotlinSerialization)

    // network
    implementation(Depends.Network.okHttp)
    implementation(Depends.Network.okHttpInterceptor)
    implementation(Depends.Network.okIO)
    implementation(Depends.Network.retrofit)
    implementation(Depends.Network.retrofitAdapter)
    implementation(Depends.Network.retrofitSerializationConverter)

    // logging
    implementation(Depends.Logging.timber)

    // test
    testImplementation(Depends.TestLibraries.jUnit)
    androidTestImplementation(Depends.TestLibraries.androidJUnit)
    androidTestImplementation(Depends.TestLibraries.espressoCore)
}
