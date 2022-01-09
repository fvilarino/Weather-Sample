plugins {
    id(Depends.ModulePlugins.libraryPlugin)
    id(Depends.ModulePlugins.kotlinPlugin)
    id(Depends.ModulePlugins.kotlinSerializationPlugin)
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = Config.Compiler.jvmTarget
        freeCompilerArgs = freeCompilerArgs + Config.Compiler.freeCompilerArgs
    }
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(project(":core:type"))
    testFixturesImplementation(project(":core:type"))
    implementation(Depends.Kotlin.kotlinSerialization)
}
