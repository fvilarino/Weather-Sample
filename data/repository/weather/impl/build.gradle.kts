plugins {
    id("base-hilt-library")
    id("kotlinx-serialization")
}

android {
    buildTypes {
        all {
            buildConfigField("String", "WEATHER_SERVICE_BASE_URL", "\"${Keys.weatherServiceBaseUrl}\"")
            buildConfigField("String", "RAPID_SERVICE_KEY", "\"${Keys.rapidServiceKey}\"")
            buildConfigField("String", "RAPID_SERVICE_WEATHER_HOST", "\"${Keys.rapidServiceWeatherHost}\"")
        }
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + Config.Compiler.serializationFreeCompileArgs
    }
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":core:type"))
    implementation(project(":data:repository:weather:api"))

    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)
    implementation(libs.com.jakewharton.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.espresso.core)
}
