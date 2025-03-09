plugins {
    id("weathersample.android.library")
    id("weathersample.dependency.injection")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.data.repository.recents.impl"
    buildFeatures {
        androidResources = false
    }
}

ksp {
    arg(RecentsRoomSchemaArgProvider(File(projectDir, "schemas")))
}

dependencies {

    implementation(projects.data.repository.recents.recentsrepoApi)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
    implementation(libs.androidx.room.room.ktx)
    implementation(libs.androidx.room.room.runtime)
    ksp(libs.androidx.room.room.compiler)

    testImplementation(libs.bundles.android.test)
    testImplementation(libs.androidx.room.room.testing)
    testImplementation(libs.org.robolectric.robolectric)
}

internal class RecentsRoomSchemaArgProvider(
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    val schemaDir: File,
) : CommandLineArgumentProvider {

    override fun asArguments(): Iterable<String> = listOf("room.schemaLocation=${schemaDir.path}")
}
