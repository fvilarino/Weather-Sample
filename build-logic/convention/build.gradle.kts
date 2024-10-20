plugins {
    `kotlin-dsl`
}

group = "com.francescsoftware.weathersample.buildconvention"

java {
    toolchain {
        val jdkVersion = libs.versions.jdk.version.get().toInt()
        languageVersion.set(JavaLanguageVersion.of(jdkVersion))
    }
}

dependencies {
    compileOnly(libs.com.android.tools.build.gradle.plugin)
    compileOnly(libs.org.jetbrains.kotlin.kotlin.gradle.plugin)
    compileOnly(libs.io.gitlab.arturbosch.detekt.plugin)
    compileOnly(libs.com.squareup.anvil.gradle.plugin)
    implementation(libs.com.android.compose.screenshot.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "weathersample.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "weathersample.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "weathersample.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "weathersample.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidTestLibrary") {
            id = "weathersample.android.library.test"
            implementationClass = "AndroidLibraryTestConventionPlugin"
        }
        register("androidLibraryComposeTest") {
            id = "weathersample.android.library.compose.test"
            implementationClass = "AndroidLibraryComposeTestConventionPlugin"
        }
        register("androidTest") {
            id = "weathersample.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("androidDetekt") {
            id = "weathersample.android.library.detekt"
            implementationClass = "AndroidLibraryDetektConventionPlugin"
        }
        register("androidBenchmarkTest") {
            id = "weathersample.android.test.macrobenchmark"
            implementationClass = "AndroidMacroBenchmarkTestConventionPlugin"
        }
        register("androidFeature") {
            id = "weathersample.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("dependencyInjection") {
            id = "weathersample.dependency.injection"
            implementationClass = "DependencyInjectionConventionPlugin"
        }
        register("kotlinLibrary") {
            id = "weathersample.kotlin.library"
            implementationClass = "KotlinLibraryConventionPlugin"
        }
        register("kotlinTestLibrary") {
            id = "weathersample.kotlin.library.test"
            implementationClass = "KotlinTestLibraryConventionPlugin"
        }
    }
}
