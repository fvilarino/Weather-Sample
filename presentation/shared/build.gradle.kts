plugins {
    id(Depends.ModulePlugins.libraryPlugin)
    id(Depends.ModulePlugins.kotlinPlugin)
    id(Depends.ModulePlugins.daggerHiltPlugin)
    kotlin(Depends.ModulePlugins.kotlinKapt)
    id(Depends.ModulePlugins.testFixturesPlugin)
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
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(project(":core:dispatcher"))
    implementation(project(":styles"))

    implementation(Depends.Android.ktx)
    implementation(Depends.Android.appCompat)
    implementation(Depends.Material.material)
    implementation(Depends.Android.archLifeCycleViewModel)
    implementation(Depends.Android.lifecycleCommon)

    implementation(Depends.Kotlin.coroutinesCore)
    implementation(Depends.Kotlin.coroutinesAndroid)

    implementation(Depends.Compose.composeUi)
    implementation(Depends.Compose.composeMaterial)
    implementation(Depends.Compose.composeUiTooling)

    implementation(Depends.Hilt.daggerHiltAndroid)
    kapt(Depends.Hilt.daggerHiltAndroidCompiler)

    implementation(Depends.Logging.timber)

    testImplementation(project(":testing"))
}