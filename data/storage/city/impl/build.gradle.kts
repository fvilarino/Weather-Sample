import com.google.protobuf.gradle.*

plugins {
    id("base-hilt-library")
    alias(libs.plugins.com.google.protobuf)
}

dependencies {
    implementation(project(":data:storage:city:api"))

    implementation(libs.androidx.datastore.datastore)
    implementation(libs.com.google.protobuf.protobuf.javalite)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)

    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.espresso.core)
}

protobuf {
    protoc {
        artifact = libs.com.google.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins{
                create("java") {
                    option("lite")
                }
            }
        }
    }
}
