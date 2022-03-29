import gradle.kotlin.dsl.accessors._5afc2a83e4e1970fd41bc894f1258c0f.kotlinOptions

plugins {
    id("base-hilt-library")
}

val catalogs = extensions.getByType<VersionCatalogsExtension>()
val composeVersion = catalogs
    .named("libs")
    .findVersion("androidx-compose-version")
    .get()
    .requiredVersion

android {
    buildFeatures {
        compose = true
    }
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + Config.Compiler.composeFreeCompileArgs
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }
}
