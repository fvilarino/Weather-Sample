plugins {
    id("base-test-library")
}

android {
    namespace = "com.francescsoftware.weathersample.testing.macrobenchmark"

    buildTypes {
        create("benchmark") {
            isDebuggable = true
            signingConfig = getByName("debug").signingConfig
            matchingFallbacks += listOf("release")
        }
    }

    targetProjectPath = ":app"
    experimentalProperties["android.experimental.self-instrumenting"] = true
}

dependencies {
    implementation(libs.androidx.test.ext.junit.ktx)
    implementation(libs.androidx.test.espresso.espresso.core)
    implementation(libs.androidx.test.uiautomator.uiautomator)
    implementation(libs.androidx.benchmark.benchmark.macro.junit4)
}

androidComponents {
    beforeVariants(selector().all()) {
        it.enable = it.buildType == "benchmark"
    }
}
