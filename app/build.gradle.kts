import java.io.FileInputStream
import java.util.Properties

plugins {
    id("weathersample.android.application")
    id("weathersample.dependency.injection")
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

    buildFeatures {
        buildConfig = true
    }
}

fun loadSigningProperties(file: File) =
    Properties().apply {
        load(FileInputStream(file))
    }

dependencies {
    implementation(projects.core.connectivity.connectivityApi)
    implementation(projects.core.connectivity.connectivityImpl)
    implementation(projects.core.coroutines)
    implementation(projects.core.dispatcher)
    implementation(projects.core.injection)
    implementation(projects.core.location.locationApi)
    implementation(projects.core.location.locationImpl)
    implementation(projects.core.time.timeApi)
    implementation(projects.core.time.timeImpl)
    implementation(projects.core.type.either)
    implementation(projects.core.type.location)
    implementation(projects.core.type.weather)

    implementation(projects.data.network)
    implementation(projects.data.persistence.settings.settingsApi)
    implementation(projects.data.persistence.settings.settingsImpl)
    implementation(projects.data.repository.city.cityrepoApi)
    implementation(projects.data.repository.city.cityrepoImpl)
    implementation(projects.data.repository.favorite.favoriterepoApi)
    implementation(projects.data.repository.favorite.favoriterepoImpl)
    implementation(projects.data.repository.recents.recentsrepoApi)
    implementation(projects.data.repository.recents.recentsrepoImpl)
    implementation(projects.data.repository.weather.weatherrepoApi)
    implementation(projects.data.repository.weather.weatherrepoImpl)

    implementation(projects.domain.interactor.city.cityinteractorApi)
    implementation(projects.domain.interactor.city.cityinteractorImpl)
    implementation(projects.domain.interactor.foundation)
    implementation(projects.domain.interactor.preferences.preferencesinteractorApi)
    implementation(projects.domain.interactor.preferences.preferencesinteractorImpl)
    implementation(projects.domain.interactor.weather.weatherinteractorApi)
    implementation(projects.domain.interactor.weather.weatherinteractorImpl)

    implementation(projects.ui.feature.favorites)
    implementation(projects.ui.feature.home)
    implementation(projects.ui.feature.search)
    implementation(projects.ui.feature.settings)
    implementation(projects.ui.shared.assets)
    implementation(projects.ui.shared.composable.common)
    implementation(projects.ui.shared.composable.weather)
    implementation(projects.ui.shared.deeplink)
    implementation(projects.ui.shared.navigation)
    implementation(projects.ui.shared.styles)
    implementation(projects.ui.shared.weathericon)

    implementation(libs.androidx.activity.activity.compose)
    implementation(libs.androidx.app.compat)
    implementation(libs.androidx.datastore.datastore)
    implementation(libs.androidx.paging.paging.common.ktx)
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
