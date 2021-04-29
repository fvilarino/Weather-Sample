plugins {
    id(Depends.ModulePlugins.libraryPlugin)
    id(Depends.ModulePlugins.kotlinPlugin)
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
        freeCompilerArgs = freeCompilerArgs + Config.Compiler.freeCompilerArgs
    }
}

dependencies {
    implementation(project(":utils"))

    implementation(Depends.Kotlin.coroutinesCore)
    implementation(Depends.TestLibraries.coroutinesTest)
    implementation(Depends.TestLibraries.jUnit)
    implementation(Depends.TestLibraries.coreTesting)
    implementation(Depends.TestLibraries.mockk)
}
