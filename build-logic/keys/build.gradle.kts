plugins {
    `kotlin-dsl`
}

group = "com.francescsoftware.weathersample.keys"

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
        register("keysLoader") {
            id = "weathersample.keys.loader"
            implementationClass = "KeysLoaderPlugin"
        }
    }
}
