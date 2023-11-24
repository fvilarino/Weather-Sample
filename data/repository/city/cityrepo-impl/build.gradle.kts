plugins {
    id("weathersample.android.library")
    id("weathersample.dependency.injection")
    id("weathersample.android.library.test")
    id("weathersample.keys.loader")
    alias(libs.plugins.org.jetbrains.kotlin.serialization)
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
        androidResources = false
    }
}

dependencies {
    implementation(project(":core:dispatcher"))
    implementation(project(":core:network"))
    implementation(project(":data:repository:city:cityrepo-api"))
    testImplementation(testFixtures(project(":core:dispatcher")))

    implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)
    implementation(libs.androidx.paging.paging.common.ktx)
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)
    testImplementation(libs.com.squareup.okhttp3.mockwebserver)
}
