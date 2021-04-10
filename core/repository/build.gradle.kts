import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlinx-serialization")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    compileSdk = rootProject.extra.get("compileSdkVersion") as Int

    defaultConfig {
        minSdk = rootProject.extra.get("minSdkVersion") as Int
        targetSdk = rootProject.extra.get("targetSdkVersion") as Int
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        all {
            buildConfigField("String", "CITY_SERVICE_BASE_URL", "\"${rootProject.extra.get("cityServiceBaseUrl")}\"")
            buildConfigField("String", "WEATHER_SERVICE_BASE_URL", "\"${rootProject.extra.get("weatherServiceBaseUrl")}\"")
            buildConfigField("String", "RAPID_SERVICE_KEY", "\"${rootProject.extra.get("rapidServiceKey")}\"")
            buildConfigField("String", "RAPID_SERVICE_CITY_HOST", "\"${rootProject.extra.get("rapidServiceCityHost")}\"")
            buildConfigField("String", "RAPID_SERVICE_WEATHER_HOST", "\"${rootProject.extra.get("rapidServiceWeatherHost")}\"")
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + "-Xallow-result-return-type" + "-Xuse-experimental=kotlin.time.ExperimentalTime"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.3.2")

    implementation("com.google.dagger:hilt-android:2.33-beta")
    kapt("com.google.dagger:hilt-android-compiler:2.33-beta")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.squareup.okio:okio:2.10.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    implementation("com.jakewharton.timber:timber:4.7.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}