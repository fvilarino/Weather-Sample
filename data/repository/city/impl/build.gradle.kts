plugins {
    id("base-hilt-library")
    id("kotlinx-serialization")
}

android {
    namespace = "com.francescsoftware.weathersample.cityrepository.impl"
    buildTypes {
        all {
            buildConfigField("String", "CITY_SERVICE_BASE_URL", "\"${Keys.cityServiceBaseUrl}\"")
            buildConfigField("String", "RAPID_SERVICE_KEY", "\"${Keys.rapidServiceKey}\"")
            buildConfigField("String", "RAPID_SERVICE_CITY_HOST", "\"${Keys.rapidServiceCityHost}\"")
        }
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + Config.Compiler.serializationFreeCompileArgs
    }
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":core:type"))
    implementation(project(":data:repository:city:api"))

    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)

    implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)

    implementation(libs.com.jakewharton.timber)

    testImplementation(libs.bundles.junit)
    androidTestImplementation(libs.bundles.android.test)
}
