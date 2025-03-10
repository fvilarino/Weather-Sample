plugins {
    id("weathersample.android.library")
    id("weathersample.dependency.injection")
    id("weathersample.android.library.test")
    alias(libs.plugins.com.google.protobuf)
}

android {
    namespace = "com.francescsoftware.weathersample.data.persistence.settings.impl"
    buildFeatures {
        androidResources = false
    }
}

dependencies {
    implementation(projects.core.coroutines)
    implementation(projects.core.dispatcher)
    implementation(projects.data.persistence.settings.settingsApi)

    implementation(libs.androidx.datastore.datastore)
    implementation(libs.com.google.protobuf.protobuf.kotlin.lite)
}

protobuf {
    protoc {
        artifact = libs.com.google.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
                create("kotlin") {
                    option("lite")
                }
            }
        }
    }
}
