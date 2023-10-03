plugins {
    id("weathersample.android.library")
    id("weathersample.android.di")
    id("weathersample.android.library.test")
}

android {
    namespace = "com.francescsoftware.weathersample.data.repository.recents.impl"
}

ksp {
    arg(RecentsRoomSchemaArgProvider(File(projectDir, "schemas")))
}

dependencies {

    implementation(project(":data:repository:recents:recentsrepo-api"))
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
