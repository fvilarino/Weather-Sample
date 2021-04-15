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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = Config.Compiler.jvmTarget
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(Depends.Kotlin.kotlinStdLib)
    implementation(Depends.Kotlin.collectionsImmutable)
    implementation(Depends.Compose.composeUi)
    implementation(Depends.Compose.composeMaterial)
    implementation(Depends.Compose.composeUiTooling)
    implementation(Depends.Material.material)
}
