plugins {
    id(Depends.ModulePlugins.libraryPlugin)
    id(Depends.ModulePlugins.kotlinPlugin)
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.composeVersion
    }
}

dependencies {
    implementation(Depends.Compose.composeUi)
    implementation(Depends.Compose.composeMaterial)
    implementation(Depends.Compose.composeUiTooling)
    implementation(Depends.Material.material)
}
