plugins {
    id("weathersample.android.library")
    id("weathersample.android.hilt")
    id("weathersample.android.library.test")
    id("weathersample.keys.loader")
    id("kotlinx-serialization")
}

android {
    namespace = "com.francescsoftware.weathersample.weatherrepository.impl"
    val rapidApiKey = configKeys.rapidApiKey
    buildTypes {
        all {
            buildConfigField(
                "String",
                "WEATHER_SERVICE_BASE_URL",
                "\"https://community-open-weather-map.p.rapidapi.com/\""
            )
            buildConfigField("String", "RAPID_SERVICE_KEY", "\"$rapidApiKey\"")
            buildConfigField("String", "RAPID_SERVICE_WEATHER_HOST", "\"weatherapi-com.p.rapidapi.com\"")
        }
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
    }
}

dependencies {
    implementation(project(":core:dispatcher"))
    implementation(project(":core:network"))
    implementation(project(":core:type"))
    implementation(project(":data:repository:weather:api"))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)
}
