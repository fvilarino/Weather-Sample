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
        all {
            buildConfigField("String", "WEATHER_SERVICE_BASE_URL", "\"${Keys.weatherServiceBaseUrl}\"")
            buildConfigField("String", "RAPID_SERVICE_KEY", "\"${Keys.rapidServiceKey}\"")
            buildConfigField("String", "RAPID_SERVICE_WEATHER_HOST", "\"${Keys.rapidServiceWeatherHost}\"")
        }

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
    implementation(project(":core:network"))
    implementation(project(":core:type"))
    implementation(project(":data:repository:weather:api"))

    implementation(Depends.Hilt.daggerHiltAndroid)
    kapt(Depends.Hilt.daggerHiltAndroidCompiler)

    implementation(Depends.Kotlin.kotlinSerialization)
    implementation(Depends.Network.okHttp)
    implementation(Depends.Network.okHttpInterceptor)
    implementation(Depends.Network.retrofit)
    implementation(Depends.Network.retrofitAdapter)
    implementation(Depends.Network.retrofitSerializationConverter)

    implementation(Depends.Logging.timber)

    testImplementation(Depends.TestLibraries.jUnit)
    androidTestImplementation(Depends.TestLibraries.androidJUnit)
    androidTestImplementation(Depends.TestLibraries.espressoCore)
}
