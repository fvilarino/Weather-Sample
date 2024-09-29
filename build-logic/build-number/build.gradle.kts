plugins {
    `kotlin-dsl`
}

group = "com.francescsoftware.weathersample.buildnumber"

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
        register("buildNumber") {
            id = "weathersample.android.buildnumber"
            implementationClass = "BuildNumberPlugin"
        }
    }
}
