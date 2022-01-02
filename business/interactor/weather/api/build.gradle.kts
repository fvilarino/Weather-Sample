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
}

dependencies {
    implementation(project(":core:dispatcher"))
    implementation(project(":core:time:api"))
    implementation(project(":core:type"))
    implementation(project(":data:city:api"))
    implementation(project(":data:weather:api"))
    implementation(project(":utils"))

    implementation(Depends.Android.ktx)
    implementation(Depends.Kotlin.coroutinesCore)
}
