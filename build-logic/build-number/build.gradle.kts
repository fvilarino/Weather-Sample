plugins {
    `kotlin-dsl`
}

group = "com.francescsoftware.weathersample.buildnumber"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.com.android.tools.build.gradle)
    compileOnly(libs.org.jetbrains.kotlin.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("buildNumber") {
            id = "weathersample.android.buildnumber"
            implementationClass = "BuildNumberPlugin"
        }
    }
}
