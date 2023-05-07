plugins {
    id("weathersample.android.library")
    id("weathersample.android.hilt")
    id("weathersample.android.library.test")
    id("weathersample.keys.loader")
    id("kotlinx-serialization")
}

android {
    namespace = "com.francescsoftware.weathersample.cityrepository.impl"
    val rapidApiKey = configKeys.rapidApiKey
    buildTypes {
        all {
            buildConfigField("String", "CITY_SERVICE_BASE_URL", "\"https://wft-geo-db.p.rapidapi.com/\"")
            buildConfigField("String", "RAPID_SERVICE_KEY", "\"$rapidApiKey\"")
            buildConfigField("String", "RAPID_SERVICE_CITY_HOST", "\"wft-geo-db.p.rapidapi.com\"")
        }
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:dispatcher"))
    implementation(project(":core:network"))
    implementation(project(":core:type"))
    implementation(project(":data:repository:city:api"))
    testImplementation(project(":testing:fake"))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)

    testImplementation(libs.bundles.android.test)
}
