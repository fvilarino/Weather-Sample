plugins {
    `kotlin-dsl`
}

group = "com.francescsoftware.weathersample.keys"

java {
    toolchain {
        val jdkVersion = libs.versions.jdk.version.get().toInt()
        languageVersion.set(JavaLanguageVersion.of(jdkVersion))
    }
}

dependencies {
    compileOnly(libs.com.android.tools.build.gradle.plugin)
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
