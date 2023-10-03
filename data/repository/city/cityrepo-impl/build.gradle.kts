plugins {
    id("weathersample.android.library")
    id("weathersample.android.di")
    id("weathersample.android.library.test")
    id("weathersample.keys.loader")
    id("kotlinx-serialization")
}

android {
    namespace = "com.francescsoftware.weathersample.data.repository.city.impl"
    val rapidApiKey = ConfigKeys.rapidApiKey
    buildTypes {
        all {
            buildConfigField("String", "CITY_SERVICE_BASE_URL", "\"https://wft-geo-db.p.rapidapi.com/\"")
            buildConfigField("String", "RAPID_SERVICE_KEY", "\"$rapidApiKey\"")
            buildConfigField("String", "RAPID_SERVICE_CITY_HOST", "\"wft-geo-db.p.rapidapi.com\"")
        }
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=kotlinx.serialization.ExperimentalSerializationApi",
        )
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:dispatcher"))
    implementation(project(":core:network"))
    implementation(project(":core:type:either"))
    implementation(project(":data:repository:city:cityrepo-api"))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)
    testImplementation(libs.com.squareup.okhttp3.mockwebserver)
}
