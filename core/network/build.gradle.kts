plugins {
    id(Depends.ModulePlugins.libraryPlugin)
    id(Depends.ModulePlugins.kotlinPlugin)
    id(Depends.ModulePlugins.daggerHiltPlugin)
    id(Depends.ModulePlugins.kotlinSerializationPlugin)
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
    implementation(project(":core:type"))

    implementation(Depends.Android.ktx)

    implementation(Depends.Hilt.daggerHiltAndroid)
    kapt(Depends.Hilt.daggerHiltAndroidCompiler)
    implementation(Depends.Network.okHttp)
    implementation(Depends.Network.okHttpInterceptor)
    implementation(Depends.Network.okIO)
    implementation(Depends.Network.retrofit)

    implementation(Depends.Logging.timber)

    testImplementation(Depends.TestLibraries.jUnit)
    androidTestImplementation(Depends.TestLibraries.androidJUnit)
    androidTestImplementation(Depends.TestLibraries.espressoCore)
}
