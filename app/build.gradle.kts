import java.io.FileInputStream
import java.util.Properties

plugins {
    id("weathersample.android.application")
    id("weathersample.android.di")
    id("weathersample.android.buildnumber")
}

android {
    namespace = "com.francescsoftware.weathersample"

    defaultConfig {
        applicationId = "com.francescsoftware.weathersample"
        versionCode = BuildNumber.versionCode.toInt()
    }

    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("./certs/debug.keystore")
        }

        create("release") {
            val releasePropertiesFile = rootProject.file("./certs/release.properties")
            if (releasePropertiesFile.exists()) {
                val properties = loadSigningProperties(releasePropertiesFile)
                storeFile = rootProject.file(properties["store"] as String)
                keyAlias = properties["alias"] as String
                storePassword = properties["storePass"] as String
                keyPassword = properties["keyPass"] as String
            } else {
                storeFile = rootProject.file(System.getenv("store"))
                keyAlias = System.getenv("alias")
                storePassword = System.getenv("storePass")
                keyPassword = System.getenv("keyPass")
            }
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".dev"
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "retrofit2.pro",
                "kotlin-serialization.pro",
            )
            signingConfig = signingConfigs.getByName("release")
        }
        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }
}

fun loadSigningProperties(file: File) =
    Properties().apply {
        load(FileInputStream(file))
    }

dependencies {
    implementation(project(":core:connectivity:connectivity-api"))
    implementation(project(":core:connectivity:connectivity-impl"))
    implementation(project(":core:dispatcher"))
    implementation(project(":core:injection"))
    implementation(project(":core:network"))
    implementation(project(":core:time:time-api"))
    implementation(project(":core:time:time-impl"))
    implementation(project(":core:type:either"))
    implementation(project(":core:type:weather"))
    implementation(project(":data:repository:city:cityrepo-api"))
    implementation(project(":data:repository:city:cityrepo-impl"))
    implementation(project(":data:repository:favorite:favoriterepo-api"))
    implementation(project(":data:repository:favorite:favoriterepo-impl"))
    implementation(project(":data:repository:recents:recentsrepo-api"))
    implementation(project(":data:repository:recents:recentsrepo-impl"))
    implementation(project(":data:repository:weather:weatherrepo-api"))
    implementation(project(":data:repository:weather:weatherrepo-impl"))
    implementation(project(":domain:interactor:city:cityinteractor-api"))
    implementation(project(":domain:interactor:city:cityinteractor-impl"))
    implementation(project(":domain:interactor:weather:weatherinteractor-api"))
    implementation(project(":domain:interactor:weather:weatherinteractor-impl"))

    implementation(project(":ui:feature:favorites"))
    implementation(project(":ui:feature:home"))
    implementation(project(":ui:feature:search"))
    implementation(project(":ui:shared:assets"))
    implementation(project(":ui:shared:composable:common"))
    implementation(project(":ui:shared:composable:weather"))
    implementation(project(":ui:shared:deviceclass"))
    implementation(project(":ui:shared:styles"))
    implementation(project(":ui:shared:weathericon"))

    implementation(libs.androidx.activity.activity.compose)
    implementation(libs.androidx.app.compat)
    implementation(libs.com.jakewharton.timber)
    implementation(libs.bundles.circuit)
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)
    androidTestImplementation(libs.androidx.test.runner)
}

afterEvaluate {
    tasks
        .asSequence()
        .filter { task ->
            task.name.equals("clean", ignoreCase = true) ||
                task.name.contains("assemble", ignoreCase = true)
        }.forEach { task ->
            task.dependsOn(":analysis:installGitHooks")
        }
}
