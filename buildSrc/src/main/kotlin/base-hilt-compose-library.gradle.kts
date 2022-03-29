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
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }
}
