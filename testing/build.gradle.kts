plugins {
    id(Depends.ModulePlugins.libraryPlugin)
    id(Depends.ModulePlugins.kotlinPlugin)
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
    implementation(project(":business:interactor:city:api"))
    implementation(project(":core:dispatcher"))
    implementation(project(":core:type"))
    implementation(project(":data:repository:city:api"))
    implementation(project(":data:storage:city:api"))
    implementation(project(":presentation:shared"))
    implementation(project(":utils"))

    api(Depends.Kotlin.coroutinesCore)
    api(Depends.TestLibraries.coroutinesTest)
    api(Depends.TestLibraries.junitJupiterApi)
    api(Depends.TestLibraries.junitJupiterParams)
    api(Depends.TestLibraries.junitJupiterEngine)
    api(Depends.TestLibraries.junitJupiterVintageEngine)
    api(Depends.TestLibraries.coreTesting)
    api(Depends.TestLibraries.mockk)
}
