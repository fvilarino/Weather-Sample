plugins {
    id("weathersample.android.library")
    id("weathersample.dependency.injection")
    id("weathersample.android.library.test")
    id("weathersample.keys.loader")
    alias(libs.plugins.org.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.francescsoftware.weathersample.data.repository.weather.impl"
    val rapidApiKey = ConfigKeys.rapidApiKey
    buildTypes {
        all {
            buildConfigField(
                "String",
                "WEATHER_SERVICE_BASE_URL",
                "\"https://community-open-weather-map.p.rapidapi.com/\"",
            )
            buildConfigField("String", "RAPID_SERVICE_KEY", "\"$rapidApiKey\"")
            buildConfigField("String", "RAPID_SERVICE_WEATHER_HOST", "\"weatherapi-com.p.rapidapi.com\"")
        }
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=kotlinx.serialization.ExperimentalSerializationApi",
        )
    }

    buildFeatures {
        buildConfig = true
        androidResources = false
    }
}

dependencies {
    implementation(project(":core:dispatcher"))
    implementation(project(":core:network"))
    implementation(project(":core:type:either"))
    implementation(project(":data:repository:weather:weatherrepo-api"))
    testImplementation(testFixtures(project(":core:dispatcher")))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)
    testImplementation(libs.com.squareup.okhttp3.mockwebserver)
}
