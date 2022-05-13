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
