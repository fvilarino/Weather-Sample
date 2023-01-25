plugins {
    id("base-hilt-library")
    alias(libs.plugins.com.google.protobuf)
}

android {
    namespace = "com.francescsoftware.weathersample.storage.city.impl"
}

dependencies {
    implementation(project(":data:storage:city:api"))

    implementation(libs.androidx.datastore.datastore)
    implementation(libs.com.google.protobuf.protobuf.javalite)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)

    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)

    testImplementation(libs.bundles.junit)
    androidTestImplementation(libs.bundles.android.test)
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
            }
        }
    }
}
